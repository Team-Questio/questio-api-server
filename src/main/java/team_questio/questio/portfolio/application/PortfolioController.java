package team_questio.questio.portfolio.application;

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
import team_questio.questio.portfolio.application.dto.PortfolioRequest;
import team_questio.questio.portfolio.application.dto.PortfolioResponse;
import team_questio.questio.portfolio.service.facade.PortfolioFacade;

@Slf4j
@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioFacade portfolioFacade;

    @PostMapping()
    public ResponseEntity<Void> createPortfolio(@RequestBody PortfolioRequest request) {
        var portfolioId = portfolioFacade.createPortfolio(request.toPortfolioParam());

        return ResponseEntity.created(URI.create("/api/v1/portfolio/" + portfolioId))
                .build();
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable Long portfolioId) {
        var portfolioInfo = portfolioFacade.getPortfolio(portfolioId);

        var response = PortfolioResponse.from(portfolioInfo);
        return ResponseEntity.ok()
                .body(response);
    }
}
