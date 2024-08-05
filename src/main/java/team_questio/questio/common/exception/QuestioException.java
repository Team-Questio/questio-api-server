package team_questio.questio.common.exception;

import org.springframework.http.HttpStatus;
import team_questio.questio.common.exception.code.ErrorCode;

public class QuestioException extends RuntimeException {
    private final ErrorCode errorCode;

    public QuestioException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public HttpStatus httpStatus() {
        return errorCode.httpStatus();
    }

    public String code() {
        return errorCode.code();
    }

    public String message() {
        return errorCode.message();
    }
}
