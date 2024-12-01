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
}
