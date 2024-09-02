package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public enum VideoError implements ErrorCode {
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "V001", "비디오 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    VideoError(HttpStatus httpStatus, String code, String message) {
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
