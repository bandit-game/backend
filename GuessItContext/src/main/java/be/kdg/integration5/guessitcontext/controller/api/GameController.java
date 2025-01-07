package be.kdg.integration5.guessitcontext.controller.api;

import be.kdg.integration5.guessitcontext.domain.Session;
import be.kdg.integration5.guessitcontext.domain.SessionId;
import be.kdg.integration5.guessitcontext.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class GameController {

    private final SessionService sessionService;

    public GameController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> getTargetNumber(@PathVariable UUID id, @RequestParam Boolean targetNumber) {
        Session session = sessionService.findById(new SessionId(id));

        if (session.isFinished()) {
            Integer target = session.getTargetNumber(); // Assuming Session has a getTargetNumber method
            return ResponseEntity.ok(Map.of("targetNumber", target));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Session not finished"));
    }
}
