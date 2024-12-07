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
    private int squareNumber;
    private PlayedPosition playedPosition;
    private Piece placedPiece;

    public Square(Board board, int squareNumber) {
        this.board = board;
        this.squareNumber = squareNumber;
    }

    public Square(Board board, int squareNumber, PlayedPosition playedPosition) {
        this.board = board;
        this.squareNumber = squareNumber;
        this.playedPosition = playedPosition;
    }

    public boolean isEmpty() {
        return placedPiece == null;
    }

    public Piece getPiece() {
        return placedPiece;
    }

    public void setPlacedPiece(Piece piece) {
        if (this.playedPosition == null)
            throw new IllegalStateException("Played position is null [square number: %s]".formatted(squareNumber));

        if ((this.playedPosition.x() + this.playedPosition.y()) % 2 != 1)
            throw new IllegalArgumentException("Is not a playable square (x: %s, y: %s)".formatted(this.playedPosition.x(), this.playedPosition.y()));

        if (!isEmpty())
            throw new IllegalStateException("Square is already occupied by another piece.");

        this.placedPiece = piece;
    }

    public void removePiece() {
        this.placedPiece = null;
    }
}
