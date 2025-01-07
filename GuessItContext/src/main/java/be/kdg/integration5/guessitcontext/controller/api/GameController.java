package be.kdg.integration5.guessitcontext.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {



    @PostMapping("/move")
    public ResponseEntity<?> makeMove(@RequestParam String userId, @RequestParam int guess) {


        return ResponseEntity.ok(null);
    }
}
