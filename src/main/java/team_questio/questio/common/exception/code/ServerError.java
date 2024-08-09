package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public enum ServerError implements ErrorCode {
    MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "인증번호 전송에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ServerError(HttpStatus httpStatus, String code, String message) {
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
