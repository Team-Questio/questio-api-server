package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;
import team_questio.questio.common.exception.code.ErrorCode;

public enum PortfolioError implements ErrorCode {
    QUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "일치하는 질문을 찾을 수 없습니다"),
    FEEDBACK_INVALID(HttpStatus.BAD_REQUEST, "P002", "적절하지 않은 피드백입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PortfolioError(HttpStatus httpStatus, String code, String message) {
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
