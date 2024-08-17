package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public enum AuthError implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "일치하는 유저를 찾을 수 없습니다."),
    MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "A002", "인증번호 전송에 실패했습니다."),
    CERTIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "A003", "인증번호가 만료되었거나 존재하지 않습니다."),
    CERTIFICATION_INVALID_CODE(HttpStatus.BAD_REQUEST, "A004", "인증번호가 일치하지 않습니다."),
    CERTIFICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "A005", "인증번호를 확인하는 중 오류가 발생했습니다."),
    CERTIFICATION_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "A006", "인증 정보를 찾을 수 없습니다."),
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "A007", "유효하지 않은 토큰입니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A008", "유효하지 않은 리프레시 토큰입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthError(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
