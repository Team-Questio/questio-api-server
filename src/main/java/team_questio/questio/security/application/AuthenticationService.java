package team_questio.questio.security.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.infra.RedisUtil;
import team_questio.questio.security.util.CertificationUtil;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Long EMAIL_EXPIRE_MINUTE = 5L;
    private static final Long EMAIL_CERTIFICATION_SUCCESS_EXPIRE_MINUTE = 10L;

    private final RedisUtil redisUtil;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Transactional
    public void sendEmail(String email) {
        var code = CertificationUtil.generateCertificationNumber();
        var htmlContent = createHtmlContent(code);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Questio 인증번호");
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw QuestioException.of(AuthError.MAIL_SEND_FAILED);
        }

        saveSecretCode(email, code);
    }

    @Transactional
    public void verifyCode(final String email, final String code) {
        var key = redisUtil.getEmailCertificationKey(email);
        var savedCode = redisUtil.getData(key, String.class)
                .orElseThrow(() -> QuestioException.of(AuthError.CERTIFICATION_CODE_EXPIRED));

        deleteSecretCode(email);
        if (!code.equals(savedCode)) {
            throw QuestioException.of(AuthError.CERTIFICATION_INVALID_CODE);
        }
        saveSuccessCertificationInfo(email);
    }

    private String createHtmlContent(String code) {
        var context = new Context();
        context.setVariable("verificationCode", code);
        return templateEngine.process("email-certification-template", context);
    }

    private void saveSecretCode(String email, String code) {
        var key = redisUtil.getEmailCertificationKey(email);
        boolean failed = !redisUtil.setData(key, code, EMAIL_EXPIRE_MINUTE, TimeUnit.MINUTES);
        if (failed) {
            throw QuestioException.of(AuthError.MAIL_SEND_FAILED);
        }
    }

    private void saveSuccessCertificationInfo(String email) {
        var key = redisUtil.getEmailCertificationSuccessKey(email);
        boolean failed = !redisUtil.setData(key, Boolean.TRUE, EMAIL_CERTIFICATION_SUCCESS_EXPIRE_MINUTE,
                TimeUnit.MINUTES);

        if (failed) {
            throw QuestioException.of(AuthError.CERTIFICATION_ERROR);
        }
    }

    private void deleteSecretCode(String email) {
        var key = redisUtil.getEmailCertificationKey(email);
        redisUtil.deleteData(key);
    }
}
