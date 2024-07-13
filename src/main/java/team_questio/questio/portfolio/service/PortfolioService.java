package team_questio.questio.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_questio.questio.gpt.service.GPTService;
import team_questio.questio.gpt.service.dto.GptParam;
import team_questio.questio.portfolio.persistence.PortfolioRepository;
import team_questio.questio.portfolio.service.dto.PortfolioParam;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final GPTService gptService;

    public Long createPortfolio(PortfolioParam portfolioParam) {
        var portfolio = portfolioParam.toEntity();
        portfolio = portfolioRepository.save(portfolio);

        gptService.generateQuestion(GptParam.of(portfolio.getId(), portfolio.getContent()));
        return portfolio.getId();
    }
}
