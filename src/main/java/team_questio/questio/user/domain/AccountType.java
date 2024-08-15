package team_questio.questio.user.domain;

public enum AccountType {
    NORMAL,
    KAKAO,
    GOOGLE,
    NAVER;

    public static AccountType of(String accountType) {
        return AccountType.valueOf(accountType.toUpperCase());
    }
}
