package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public enum PortfolioError implements ErrorCode {
    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "포트폴리오를 찾을 수 없습니다."),
    PORTFOLIO_NOT_OWN(HttpStatus.UNAUTHORIZED, "P002", "포트폴리오에 접근할 수 없습니다."),
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
