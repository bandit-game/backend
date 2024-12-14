package be.kdg.integration5.checkerscontext.adapter.out.dto;

import be.kdg.integration5.checkerscontext.adapter.in.dto.PiecePositionGetDto;
import be.kdg.integration5.checkerscontext.domain.Move;

import java.util.List;
import java.util.Objects;

public record MoveGetDto(Move.MoveType moveType, PiecePositionGetDto initialPosition, List<PiecePositionGetDto> intermediatePosition, PiecePositionGetDto finalPosition) {
    public MoveGetDto {
        Objects.requireNonNull(moveType);
        Objects.requireNonNull(initialPosition);
        Objects.requireNonNull(finalPosition);
        Objects.requireNonNull(intermediatePosition);
    }

    public static MoveGetDto of(Move move) {
        return new MoveGetDto(
                move.getType(),
                PiecePositionGetDto.of(move.getInitialPosition()),
                move.getIntermediateAttackPositions().stream().map(PiecePositionGetDto::of).toList(),
                PiecePositionGetDto.of(move.getFuturePosition())
        );
    }
}
