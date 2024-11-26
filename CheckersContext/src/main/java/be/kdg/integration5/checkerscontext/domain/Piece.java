package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@ToString
public class Piece {
    private boolean isKing;
    private PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
    }
    //    private Player owner;

    public enum PieceColor {
        WHITE, BLACK
    }
}


