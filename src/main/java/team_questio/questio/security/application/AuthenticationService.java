package team_questio.questio.security.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.ServerError;
import team_questio.questio.security.util.MailCertificationUtil;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(String email) {
        var htmlContent = createHtmlContent(MailCertificationUtil.generateCertificationNumber());
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Questio 인증번호");
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw QuestioException.of(ServerError.MAIL_SEND_FAILED);
        }
    }

    private String createHtmlContent(String code) {
        var context = new Context();
        context.setVariable("verificationCode", code);
        return templateEngine.process("email-certification-template", context);
    }
}
