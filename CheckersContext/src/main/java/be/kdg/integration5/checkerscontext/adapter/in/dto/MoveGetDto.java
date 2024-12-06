package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.Move;

import java.util.Objects;

public record MoveGetDto(Integer currentX, Integer currentY, Integer futureX, Integer futureY) {
    public MoveGetDto {
        Objects.requireNonNull(currentX);
        Objects.requireNonNull(currentY);
        Objects.requireNonNull(futureX);
        Objects.requireNonNull(futureY);
    }

    public static MoveGetDto of(Move move) {
        return new MoveGetDto(
                move.getInitialPosition().x(),
                move.getInitialPosition().y(),
                move.getFuturePosition().x(),
                move.getFuturePosition().y()
        );
    }
}
