package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayableSquare extends Square {
    private Piece placedPiece;
}
