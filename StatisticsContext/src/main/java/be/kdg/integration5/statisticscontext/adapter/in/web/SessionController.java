package be.kdg.integration5.statisticscontext.adapter.in.web;


import be.kdg.integration5.statisticscontext.port.in.CollectSessionStatisticsCsvUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final CollectSessionStatisticsCsvUseCase collectSessionStatisticsCsvUseCase;

    public SessionController(CollectSessionStatisticsCsvUseCase collectSessionStatisticsCsvUseCase) {
        this.collectSessionStatisticsCsvUseCase = collectSessionStatisticsCsvUseCase;
    }

    @GetMapping("/export/csv")
    @PreAuthorize("hasRole('admin')")
    public void exportSessionStatisticsCsv(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"games_statistics.csv\"");

        try (OutputStream outputStream = response.getOutputStream()) {
            collectSessionStatisticsCsvUseCase.exportSessionData(outputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export session statistics CSV", e);
        }
    }
}
