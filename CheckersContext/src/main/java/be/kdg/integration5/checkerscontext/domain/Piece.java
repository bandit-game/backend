package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.domain.exception.PiecePlacedNotOnPlayableSquareException;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
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

    /*public List<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();

        moves.addAll(findAllAttackMoves());
        if (!moves.isEmpty())
            return moves;

        moves.addAll(findAllGoMoves());
        return moves;
    }

    private List<Move> findAllAttackMovesForSquare(int x, int y) {
        return findAllAttackMovesForSquare(x, y, null);
    }

    private List<Move> findAllAttackMovesForSquare(int x, int y, MoveDirection fromDirection) {
        List<Move> attackMoves = new ArrayList<>();

        List<MoveDirection> directions = new ArrayList<>();
        for (MoveDirection direction : MoveDirection.values())
            if (direction != fromDirection)
                directions.add(direction);


        for (MoveDirection direction : directions) {
            List<Move> diagonalAttackMoves = findDiagonalAttackMoves(...);
            for (Move attackMove : diagonalAttackMoves) {
                int attackedX = attackMove.getFuturePosition().x();
                int attackedY = attackMove.getFuturePosition().y();
                List<Move> attackMovesForNextSquare = findAllAttackMovesForSquare(attackedX, attackedY, direction);
                if (!attackMovesForNextSquare.isEmpty()) {
                    for (Move nextAttackMove : attackMovesForNextSquare) {
                        List<Move> movesToJoin = diagonalAttackMoves.stream().filter(diagonalAttackMove -> diagonalAttackMove.getFuturePosition().equals(nextAttackMove.getInitialPosition())).toList();
                        for (Move moveToJoin : movesToJoin) {
                            moveToJoin.join(nextAttackMove);
                        }
                    }
                }
            }


            attackMoves.addAll(diagonalAttackMoves);
        }

        return ;
    }

    private List<Move> findDiagonalAttackMoves() {
        return null;
    }

    private List<Move> findAllGoMoves() {
        return null;
    }*/

    public List<Move> getPossibleMoves() {
        List<Move> attackMoves = new ArrayList<>();
        List<Move> goMoves = new ArrayList<>();

        Square currentSquare = this.getSquare();

        int currentX = currentSquare.getPlayedPosition().x();
        int currentY = currentSquare.getPlayedPosition().y();
        Square[][] squares = currentSquare.getBoard().getSquares();

        for (MoveDirection direction : MoveDirection.values()) {
            int newX = currentX + direction.xShift;
            int newY = currentY + direction.yShift;

            // Skip invalid forward moves for non-King pieces
            if (!isKing() && !isMovingForward(direction.yShift)) continue;

            if (isOutOfBounds(newX, newY)) continue;

            Square targetSquare = squares[newY][newX];

            if (canAttack(newX, newY, direction.xShift, direction.yShift, squares)) {
                attackMoves.add(createAttackMove(currentX, currentY, newX, newY, squares));
            } else if (targetSquare.isEmpty()) {
                goMoves.add(new Move(
                        currentSquare.getPlayedPosition(),
                        targetSquare.getPlayedPosition(),
                        Move.MoveType.GO
                ));
            }
        }

        // Prioritize ATTACK moves
        return !attackMoves.isEmpty() ? attackMoves : goMoves;
    }

    // Helper Method: Check if an attack is possible
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

    // Helper Method: Create an ATTACK move
    private Move createAttackMove(int currentX, int currentY, int enemyX, int enemyY, Square[][] squares) {
        int xShift = enemyX - currentX;
        int yShift = enemyY - currentY;

        int landingX = enemyX + xShift;
        int landingY = enemyY + yShift;

        Square landingSquare = squares[landingY][landingX];

        Move attackMove = new Move(
                new PlayedPosition(currentX, currentY),
                landingSquare.getPlayedPosition(),
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
        if (!square.isEmpty()) {
            throw new IllegalStateException("Cannot place a piece on an occupied square.");
        }
        this.square = square;
        square.setPlacedPiece(this);
    }

    public void moveToSquare(Square targetSquare) {
        if (!targetSquare.isEmpty()) {
            throw new IllegalStateException("Target square is already occupied.");
        }
        // Remove from current square
        square.removePiece();

        // Set to new square
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
