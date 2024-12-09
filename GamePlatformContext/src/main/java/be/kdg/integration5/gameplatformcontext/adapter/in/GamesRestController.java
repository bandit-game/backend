package be.kdg.integration5.gameplatformcontext.adapter.in;

import be.kdg.integration5.gameplatformcontext.adapter.in.dto.GameGetDto;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.port.in.GetAllGamesUseCase;
import be.kdg.integration5.gameplatformcontext.port.in.GetGameByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/games")
public class GamesRestController {
    private final GetAllGamesUseCase getAllGamesUseCase;
    private final GetGameByIdUseCase getGameByIdUseCase;

    @Autowired
    public GamesRestController(GetAllGamesUseCase getAllGamesUseCase, GetGameByIdUseCase getGameByIdUseCase) {
        this.getAllGamesUseCase = getAllGamesUseCase;
        this.getGameByIdUseCase = getGameByIdUseCase;
    }

    @GetMapping
    public ResponseEntity<List<GameGetDto>> getAllGames() {
        List<Game> allGames = getAllGamesUseCase.getAllGames();

        if (allGames.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(allGames.stream().map(GameGetDto::of).toList());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameGetDto> getGameById(@PathVariable UUID gameId) {
        GameId id = new GameId(gameId);
        Game game = getGameByIdUseCase.getGameById(id);
        return ResponseEntity.ok(GameGetDto.of(game));
    }
}
