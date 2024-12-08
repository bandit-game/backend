package be.kdg.integration5.checkerscontext.adapter.in.dto;

import java.util.List;
import java.util.UUID;

public record PossibleMovesForPlayerDTO(UUID playerId, List<MoveGetDto> moves) {
}
