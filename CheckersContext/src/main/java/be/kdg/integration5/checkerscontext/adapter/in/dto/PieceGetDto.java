package be.kdg.integration5.checkerscontext.adapter.in.dto;

import be.kdg.integration5.checkerscontext.domain.Piece;

public record PieceGetDto(int pieceNumber, int x, int y, boolean isKing, String pieceColor) {
    public static PieceGetDto of(Piece piece) {
        return new PieceGetDto(
                piece.getPieceNumber(),
                piece.getSquare().getX(),
                piece.getSquare().getY(),
                piece.isKing(),
                piece.getColor().name()
        );
    }
}
