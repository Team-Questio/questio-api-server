package team_questio.questio.security.util;

import java.security.SecureRandom;
import java.util.Random;

public final class MailCertificationUtil {
    private final static Random random = new SecureRandom();

    public static String generateCertificationNumber() {
        StringBuilder certificationNumber = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            certificationNumber.append(random.nextInt(10));
        }
        return certificationNumber.toString();
    }
}
