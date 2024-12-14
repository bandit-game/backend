package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.PiecePosition;

import java.util.Objects;

public record PiecePositionGetDto(Integer x, Integer y) {
    public PiecePositionGetDto {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
    }

    public static PiecePositionGetDto of(PiecePosition piecePosition) {
        return new PiecePositionGetDto(piecePosition.x(), piecePosition.y());
    }
}
