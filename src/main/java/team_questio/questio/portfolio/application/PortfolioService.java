package team_questio.questio.portfolio.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_questio.questio.portfolio.persistence.PortfolioRepository;
import team_questio.questio.portfolio.application.dto.PortfolioDetailInfo;
import team_questio.questio.portfolio.application.dto.PortfolioParam;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public Long createPortfolio(PortfolioParam portfolioParam) {
        var portfolio = portfolioParam.toEntity();
        portfolio = portfolioRepository.save(portfolio);

        return portfolio.getId();
    }

    @Transactional(readOnly = true)
    public PortfolioDetailInfo getPortfolio(Long portfolioId) {
        var portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        return PortfolioDetailInfo.from(portfolio);
    }
}
