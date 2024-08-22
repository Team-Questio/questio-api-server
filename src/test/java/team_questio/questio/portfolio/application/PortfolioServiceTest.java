package team_questio.questio.portfolio.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.PortfolioError;
import team_questio.questio.portfolio.application.dto.PortfolioDetailInfo;
import team_questio.questio.portfolio.domain.Portfolio;
import team_questio.questio.portfolio.persistence.PortfolioRepository;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {
    @Mock
    private PortfolioRepository portfolioRepository;
    @InjectMocks
    private PortfolioService portfolioService;

    @DisplayName("[포트폴리오 테스트] 포트폴리오 생성자가 아닌 사람이 접근할 경우")
    @Test
    void wrongUserApproachContent() {
        //given
        final Portfolio portfolio = new Portfolio(1L, "test");
        final Long wrongUser = 2L;
        given(portfolioRepository.findById(any()))
            .willReturn(java.util.Optional.of(portfolio));

        //when && then
        Assertions.assertThatThrownBy(() -> portfolioService.getPortfolio(1L, wrongUser))
                .isInstanceOf(QuestioException.class)
                .hasMessage(PortfolioError.PORTFOLIO_NOT_OWN.message());


    }

    @DisplayName("[포트폴리오 테스트] 포트폴리오 생성자가 접근할 경우")
    @Test
    void rightUserApproachContent() {
        //given
        final Portfolio portfolio = new Portfolio(1L, "test");
        final Long rightUser = 1L;
        given(portfolioRepository.findById(any())).willReturn(java.util.Optional.of(portfolio));

        //when
        final PortfolioDetailInfo portfolioDetailInfo = portfolioService.getPortfolio(1L, rightUser);

        //then
        Assertions.assertThat(portfolioDetailInfo).isNotNull();
    }
}