package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.PiecePosition;

import java.util.Objects;

public record PiecePositionPostDto(Integer x, Integer y) {
    public PiecePositionPostDto {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
    }

    public PiecePosition toDomain() {
        return new PiecePosition(x, y);
    }
}
