package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.Move;

import java.util.Objects;

public record MoveGetDto(Integer initialPosition, Integer futurePosition) {
    public MoveGetDto {
        Objects.requireNonNull(initialPosition);
        Objects.requireNonNull(futurePosition);
    }

    public static MoveGetDto of(Move move) {
        return new MoveGetDto(
                move.getInitialPosition().playedSquareNumber(),
                move.getFuturePosition().playedSquareNumber()
        );
    }
}
