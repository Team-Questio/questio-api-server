package team_questio.questio.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_questio.questio.portfolio.persistence.PortfolioRepository;
import team_questio.questio.portfolio.service.dto.PortfolioParam;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public Long createPortfolio(PortfolioParam portfolioParam) {
        var portfolio = portfolioParam.toEntity();
        portfolio = portfolioRepository.save(portfolio);

        return portfolio.getId();
    }
}
