package team_questio.questio.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String DEFAULT_ERROR_CODE = "errorCode";

    @ExceptionHandler(QuestioException.class)
    public ProblemDetail handleQuestioException(QuestioException e) {
        log.warn(e.message(), e);

        var problemDetail = ProblemDetail.forStatusAndDetail(e.httpStatus(), e.message());
        problemDetail.setProperty(DEFAULT_ERROR_CODE, e.code());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        log.error(e.getMessage(), e);

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        problemDetail.setProperty(DEFAULT_ERROR_CODE, "E999");

        return problemDetail;
    }
}
