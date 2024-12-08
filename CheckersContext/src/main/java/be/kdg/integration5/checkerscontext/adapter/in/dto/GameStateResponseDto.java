package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.Game;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public record GameStateResponseDto(UUID currentPlayer, List<PieceGetDto> pieces) {
    public GameStateResponseDto {
        Objects.requireNonNull(currentPlayer);
        Objects.requireNonNull(pieces);
    }
    public static GameStateResponseDto of(Game game) {
        List<PieceGetDto> pieces = Arrays.stream(game.getBoard().getSquares())
                .flatMap(Arrays::stream)
                .filter(square -> !square.isEmpty())
                .map(square -> PieceGetDto.of(square.getPlacedPiece()))
                .collect(Collectors.toList());
        return new GameStateResponseDto(
                game.getBoard().getCurrentPlayer().getPlayerId().uuid(),
                pieces
        );
    }
}
