package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Square {
    private Piece placedPiece;

    public boolean isEmpty() {
        return placedPiece == null;
    }

    public void setPlacedPiece(Piece piece) {
        if ((piece.getCurrentX() + piece.getCurrentY()) % 2 != 1)
            throw new IllegalArgumentException("Is not a playable square (x: %s, y: %s)".formatted(piece.getCurrentX(), piece.getCurrentY()));

        if (!isEmpty())
            throw new IllegalStateException("Square is already occupied by another piece.");

        this.placedPiece = piece;
    }

    public void removePiece() {
        this.placedPiece = null;
    }
}
