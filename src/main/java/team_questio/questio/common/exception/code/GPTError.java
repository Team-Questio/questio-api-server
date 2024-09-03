package team_questio.questio.common.exception.code;

import org.springframework.http.HttpStatus;

public enum GPTError implements ErrorCode {
    PORTFOLIO_WRONG_SUBJECT(HttpStatus.BAD_REQUEST, "G001", "포트폴리오 컨텐츠가 CS와 관련이 없다고 판단되었습니다."),
    PORTFOLIO_JSON_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G002", "질문 JSON 파싱 중 오류가 발생했습니다."),
    PORTFOLIO_GENERATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G003", "질문 생성 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    GPTError(HttpStatus httpStatus, String code, String message) {
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
