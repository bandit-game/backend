package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@ToString
public class Piece {
    private int pieceNumber;
    private Board board;
    private boolean isKing;
    private PieceColor color;

    public Piece(int pieceNumber, Board board, PieceColor color) {
        this.pieceNumber = pieceNumber;
        this.board = board;
        this.color = color;
    }
    //    private Player owner;

    public enum PieceColor {
        WHITE, BLACK
    }
}


