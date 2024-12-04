package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@ToString
public class PlayableSquare extends Square {
    private PlayedPosition playedPosition;
    private Piece placedPiece;

    public PlayableSquare(Board board, int squareNumber, PlayedPosition playedPosition) {
        super(board, squareNumber);
        this.playedPosition = playedPosition;
    }

    public boolean isEmpty() {
        return placedPiece == null;
    }

    public Piece getPiece() {
        return placedPiece;
    }

    public void setPiece(Piece piece) {
        if (!isEmpty()) {
            throw new IllegalStateException("Square is already occupied by another piece.");
        }
        this.placedPiece = piece;
    }

    public void removePiece() {
        this.placedPiece = null;
    }
}
