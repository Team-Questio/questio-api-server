package team_questio.questio.portfolio.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.portfolio.presentation.dto.PortfolioRequest;
import team_questio.questio.portfolio.service.PortfolioService;

@Slf4j
@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> createPortfolio(@RequestBody PortfolioRequest request) {
        var portfolioId = portfolioService.createPortfolio(request.toPortfolioParam());

        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public String hello(){
        return "Hello You Are Success";
    }


}
