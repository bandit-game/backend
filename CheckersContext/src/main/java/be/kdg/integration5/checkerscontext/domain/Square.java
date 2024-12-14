package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Square {
    private int x;
    private int y;
    private Piece placedPiece;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {
        return placedPiece == null;
    }

    public void setPlacedPiece(Piece piece) {
        if ((piece.getPiecePosition().x() + piece.getPiecePosition().y()) % 2 != 1)
            throw new IllegalArgumentException("Is not a playable square (x: %s, y: %s)".formatted(piece.getPiecePosition().x(), piece.getPiecePosition().y()));

        if (!isEmpty())
            throw new IllegalStateException("Square is already occupied by another piece.");

        piece.updateCoordinates(x, y);
        this.placedPiece = piece;
    }

    public void removePiece() {
        this.placedPiece = null;
    }
}
