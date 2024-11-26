package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PlayableSquare extends Square {
    private Position position;
    private Piece placedPiece;

    public PlayableSquare(Position position) {
        this.position = position;
    }
}
