package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Piece {
    private int currentX;
    private int currentY;
    private boolean isKing;
    private PieceColor color;
    private Player owner;

    public Piece(int currentX, int currentY, PieceColor color, Player owner) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.color = color;
        this.owner = owner;
    }

    /*public void setSquare(Square square) {
        if (!square.isEmpty())
            throw new IllegalStateException("Cannot place a piece on an occupied square. (x: %s, y: %s)".formatted(square.getX(), square.getY()));

        this.square = square;
        square.setPlacedPiece(this);
    }

    public void moveToSquare(Square targetSquare) {
        if (!targetSquare.isEmpty())
            throw new IllegalStateException("Target square is already occupied.");

        square.removePiece();

        setSquare(targetSquare);
    }*/

    public enum PieceColor {
        WHITE, BLACK
    }
}
