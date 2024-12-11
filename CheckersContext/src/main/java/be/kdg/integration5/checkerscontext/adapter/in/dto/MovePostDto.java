package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.Move;

import java.util.List;
import java.util.Objects;

public record MovePostDto(Move.MoveType moveType, PiecePositionPostDto initialPosition, List<PiecePositionPostDto> intermediatePosition, PiecePositionPostDto finalPosition) {
    public MovePostDto {
        Objects.requireNonNull(moveType);
        Objects.requireNonNull(initialPosition);
        Objects.requireNonNull(finalPosition);
    }

    public Move toDomain() {
        return new Move(
                initialPosition.toDomain(),
                intermediatePosition.stream().map(PiecePositionPostDto::toDomain).toList(),
                finalPosition.toDomain(),
                moveType
        );
    }
}
