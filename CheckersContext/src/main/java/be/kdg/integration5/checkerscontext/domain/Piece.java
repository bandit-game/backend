package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Piece {
    private PiecePosition piecePosition;
    private boolean isKing;
    private PieceColor color;
    private Player owner;

    public Piece(PiecePosition piecePosition, PieceColor color, Player owner) {
        this.piecePosition = piecePosition;
        this.color = color;
        this.owner = owner;
    }

    public void updateCoordinates(int x, int y) {
        this.piecePosition = new PiecePosition(x,y);
    }

    public void upgrade() {
        setKing(true);
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
