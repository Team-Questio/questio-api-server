package team_questio.questio.portfolio.presentation;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.portfolio.application.PortfolioFacadeService;
import team_questio.questio.portfolio.presentation.dto.PortfolioRequest;
import team_questio.questio.portfolio.presentation.dto.PortfolioResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController implements PortfolioApiController {
    private final PortfolioFacadeService portfolioFacadeService;

    @PostMapping()
    public ResponseEntity<Void> createPortfolio(@RequestBody PortfolioRequest request) {
        var portfolioId = portfolioFacadeService.createPortfolio(request.toPortfolioParam());

        return ResponseEntity.created(URI.create("/api/v1/portfolio/" + portfolioId))
                .build();
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable Long portfolioId) {
        var portfolioInfo = portfolioFacadeService.getPortfolio(portfolioId);

        var response = PortfolioResponse.from(portfolioInfo);
        return ResponseEntity.ok()
                .body(response);
    }
}
