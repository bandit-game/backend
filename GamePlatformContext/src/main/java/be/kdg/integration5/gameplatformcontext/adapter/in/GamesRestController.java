package be.kdg.integration5.gameplatformcontext.adapter.in;

import be.kdg.integration5.gameplatformcontext.adapter.in.dto.GameGetDto;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.port.in.GetAllGamesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GamesRestController {
    private final GetAllGamesUseCase getAllGamesUseCase;

    @Autowired
    public GamesRestController(GetAllGamesUseCase getAllGamesUseCase) {
        this.getAllGamesUseCase = getAllGamesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<GameGetDto>> getAllGames() {
        List<Game> allGames = getAllGamesUseCase.getAllGames();

        if (allGames.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(allGames.stream().map(GameGetDto::of).toList());
    }
}
