package be.kdg.integration5.checkerscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Square {
    private Board board;
    private int x;
    private int y;
    private Piece placedPiece;

    public Square(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {
        return placedPiece == null;
    }

    public void setPlacedPiece(Piece piece) {
        if ((this.x + this.y) % 2 != 1)
            throw new IllegalArgumentException("Is not a playable square (x: %s, y: %s)".formatted(this.x, this.y));

        if (!isEmpty())
            throw new IllegalStateException("Square is already occupied by another piece.");

        this.placedPiece = piece;
    }

    public void removePiece() {
        this.placedPiece = null;
    }
}
