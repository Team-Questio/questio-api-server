package team_questio.questio.common.exception.presentation;

import org.springframework.http.HttpStatus;
import team_questio.questio.common.exception.code.ErrorCode;

public record ExceptionCodeResponse(
        HttpStatus httpStatus,
        String code,
        String message
) {
    public static ExceptionCodeResponse from(ErrorCode errorCode) {
        return new ExceptionCodeResponse(
                errorCode.httpStatus(),
                errorCode.code(),
                errorCode.message()
        );
    }
}
