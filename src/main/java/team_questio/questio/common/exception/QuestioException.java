package team_questio.questio.common.exception;

import org.springframework.http.HttpStatus;
import team_questio.questio.common.exception.code.ErrorCode;

public class QuestioException extends RuntimeException {
    private final ErrorCode errorCode;

    private QuestioException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public static QuestioException of(ErrorCode errorCode) {
        return new QuestioException(errorCode);
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
