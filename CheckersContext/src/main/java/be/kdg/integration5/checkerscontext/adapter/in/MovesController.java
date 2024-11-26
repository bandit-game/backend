package be.kdg.integration5.checkerscontext.adapter.in;

import be.kdg.integration5.checkerscontext.adapter.in.dto.MoveGetDto;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/games/{gameId}/moves")
public class MovesController {
    private final FindAllPossibleMovesUseCase findAllPossibleMovesUseCase;

    @Autowired
    public MovesController(FindAllPossibleMovesUseCase findAllPossibleMovesUseCase) {
        this.findAllPossibleMovesUseCase = findAllPossibleMovesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<MoveGetDto>> getAllPossibleMoves(@PathVariable("gameId") UUID gameId, @RequestParam Integer position) {
        List<Move> allPossibleMoves = findAllPossibleMovesUseCase.findAllPossibleMoves(new FindAllPossibleMovesCommand(new GameId(gameId), position));
        if (allPossibleMoves.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(allPossibleMoves.stream().map(MoveGetDto::of).toList());
    }

}
