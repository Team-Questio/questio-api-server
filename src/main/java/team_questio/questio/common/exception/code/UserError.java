package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public enum UserError implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "일치하는 유저를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    UserError(HttpStatus httpStatus, String code, String message) {
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
