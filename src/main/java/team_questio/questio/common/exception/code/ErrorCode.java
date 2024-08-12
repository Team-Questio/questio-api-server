package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus httpStatus();

    String code();

    String message();
}
