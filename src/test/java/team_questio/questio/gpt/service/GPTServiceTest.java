package team_questio.questio.gpt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team_questio.questio.common.exception.code.GPTError;
import team_questio.questio.gpt.service.dto.GptParam;

@SpringBootTest
class GPTServiceTest {
    @Autowired
    private GPTService gptService;

    @Test
    @DisplayName("[GPT 테스트] 포트폴리오가 잘못된 경우 오류 발생")
    void wrongPortfolioTest() {
        var portfolio = "잘못된 포트폴리오";
        var portfolioParam = new GptParam(portfolio);

        Assertions.assertThatThrownBy(() -> gptService.generateQuestion(portfolioParam))
                .hasMessage(GPTError.PORTFOLIO_WRONG_SUBJECT.message());

    }

    @Test
    @DisplayName("[GPT 테스트] 포트폴리오가 올바른 경우 질문 생성")
    void correctPortfolioTest() {
        var portfolio = "SpringBoot를 사용한 웹 개발을 했습니다.";
        var portfolioParam = new GptParam(portfolio);

        var questions = gptService.generateQuestion(portfolioParam);

        Assertions.assertThat(questions).isNotEmpty();
    }
}