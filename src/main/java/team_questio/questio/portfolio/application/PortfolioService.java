package team_questio.questio.portfolio.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.PortfolioError;
import team_questio.questio.portfolio.application.command.PortfolioCommand;
import team_questio.questio.portfolio.application.dto.PortfolioDetailInfo;
import team_questio.questio.portfolio.persistence.PortfolioRepository;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public Long createPortfolio(PortfolioCommand portfolioCommand) {
        var portfolio = portfolioCommand.toEntity();
        portfolio = portfolioRepository.save(portfolio);

        return portfolio.getId();
    }

    @Transactional(readOnly = true)
    public PortfolioDetailInfo getPortfolio(Long portfolioId, Long userId) {
        var portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> QuestioException.of(PortfolioError.PORTFOLIO_NOT_FOUND));
        portfolio.checkOwner(userId);

        return PortfolioDetailInfo.from(portfolio);
    }

    @Transactional(readOnly = true)
    public List<PortfolioDetailInfo> getPortfolios(Long userId) {
        var portfolios = portfolioRepository.findByUserId(userId);
        return portfolios.stream()
                .map(PortfolioDetailInfo::from)
                .toList();
    }
}
