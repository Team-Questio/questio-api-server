package team_questio.questio.portfolio.presentation;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.portfolio.application.PortfolioFacadeService;
import team_questio.questio.portfolio.presentation.dto.FeedbackRequest;
import team_questio.questio.portfolio.presentation.dto.PortfolioRequest;
import team_questio.questio.portfolio.presentation.dto.PortfolioResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController implements PortfolioApiController {
    private final PortfolioFacadeService portfolioFacadeService;

    @PostMapping()
    public ResponseEntity<Void> createPortfolio(@RequestBody PortfolioRequest request,
                                                Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getPrincipal().toString());
        var command = request.toPortfolioParam(userId);
        var portfolioId = portfolioFacadeService.createPortfolio(command);

        return ResponseEntity.created(URI.create("/api/v1/portfolio/" + portfolioId))
                .build();
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable Long portfolioId,
                                                          Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getPrincipal().toString());

        var portfolioInfo = portfolioFacadeService.getPortfolio(portfolioId, userId);

        var response = PortfolioResponse.from(portfolioInfo);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<List<PortfolioResponse>> getPortfolios(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getPrincipal().toString());
        var portfolios = portfolioFacadeService.getPortfolios(userId);

        var response = portfolios.stream()
                .map(PortfolioResponse::from)
                .toList();
        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/quest/{questId}")
    public ResponseEntity<Void> updateFeedback(@PathVariable Long questId,
                                               @RequestBody FeedbackRequest request) {

        portfolioFacadeService.updateFeedback(questId, request.feedback());
        return ResponseEntity.ok().build();
    }
}
