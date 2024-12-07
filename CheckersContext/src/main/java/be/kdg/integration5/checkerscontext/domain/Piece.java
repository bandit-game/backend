package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"square"})
public class Piece {
    private int pieceNumber;
    private Square square;
    private boolean isKing;
    private PieceColor color;

    public Piece(int pieceNumber, Square square, PieceColor color) {
        this.pieceNumber = pieceNumber;
        setSquare(square);
        this.color = color;
    }

    public List<Move> getPossibleMoves() {
        List<Move> attackMoves = new ArrayList<>();
        List<Move> goMoves = new ArrayList<>();

        Square currentSquare = this.getSquare();

        int currentX = currentSquare.getX();
        int currentY = currentSquare.getY();
        Square[][] squares = currentSquare.getBoard().getSquares();

        for (MoveDirection direction : MoveDirection.values()) {
            int targetX = currentX + direction.xShift;
            int targetY = currentY + direction.yShift;

            if (!isKing() && !isMovingForward(direction.yShift)) continue;

            if (isOutOfBounds(targetX, targetY)) continue;

            Square targetSquare = squares[targetY][targetX];

            if (canAttack(targetX, targetY, direction.xShift, direction.yShift, squares)) {
                attackMoves.add(createAttackMove(currentX, currentY, targetX, targetY, squares));
            } else if (targetSquare.isEmpty()) {
                goMoves.add(new Move(
                        new MovePosition(currentX, currentY),
                        new MovePosition(targetX, targetY),
                        Move.MoveType.GO
                ));
            }
        }

        return !attackMoves.isEmpty() ? attackMoves : goMoves;
    }

    private boolean canAttack(int newX, int newY, int xShift, int yShift, Square[][] squares) {
        int landingX = newX + xShift;
        int landingY = newY + yShift;

        if (isOutOfBounds(landingX, landingY)) return false;

        Square enemySquare = squares[newY][newX];
        Square landingSquare = squares[landingY][landingX];

        return !enemySquare.isEmpty() &&
                enemySquare.getPlacedPiece().getColor() != this.color &&
                landingSquare.isEmpty();
    }

    private Move createAttackMove(int currentX, int currentY, int enemyX, int enemyY, Square[][] squares) {
        int xShift = enemyX - currentX;
        int yShift = enemyY - currentY;

        int landingX = enemyX + xShift;
        int landingY = enemyY + yShift;

        Move attackMove = new Move(
                new MovePosition(currentX, currentY),
                new MovePosition(landingX, landingY),
                Move.MoveType.ATTACK
        );
//        attackMove.addIntermediateAttackPosition(enemySquare.getPlayedPosition());
        return attackMove;
    }


    private boolean isMovingForward(int yChange) {
        return (this.color == PieceColor.WHITE && yChange < 0) || (this.color == PieceColor.BLACK && yChange > 0);
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= Board.BOARD_SIZE || y < 0 || y >= Board.BOARD_SIZE;
    }

    public void setSquare(Square square) {
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
    }

    private enum MoveDirection {
        UP_RIGHT(1, -1),
        UP_LEFT(-1, -1),
        DOWN_RIGHT(1, 1),
        DOWN_LEFT(-1, 1),;

        int xShift;
        int yShift;

        MoveDirection(int xShift, int yShift) {
            this.xShift = xShift;
            this.yShift = yShift;
        }
    }

    public enum PieceColor {
        WHITE, BLACK
    }
}
