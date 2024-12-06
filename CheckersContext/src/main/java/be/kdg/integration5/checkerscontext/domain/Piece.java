package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.domain.exception.PiecePlacedNotOnPlayableSquareException;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
public class Piece {
    private int pieceNumber;
    private Square square;
    private boolean isKing;
    private PieceColor color;

    public Piece(int pieceNumber, PlayableSquare square, PieceColor color) {
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

        if (!(this.getSquare() instanceof PlayableSquare thisPlayableSquare))
            throw new PiecePlacedNotOnPlayableSquareException("Piece Placed Not On Playable Square");

        int currentX = thisPlayableSquare.getPlayedPosition().x();
        int currentY = thisPlayableSquare.getPlayedPosition().y();
        Square[][] squares = thisPlayableSquare.getBoard().getSquares();

        for (MoveDirection direction : MoveDirection.values()) {
            int newX = currentX + direction.xShift;
            int newY = currentY + direction.yShift;

            // Skip invalid forward moves for non-King pieces
            if (!isKing() && !isMovingForward(direction.yShift)) continue;

            if (isOutOfBounds(newX, newY)) continue;

            if (!(squares[newY][newX] instanceof PlayableSquare targetPlayableSquare)) continue;

            if (canAttack(newX, newY, direction.xShift, direction.yShift, squares)) {
                attackMoves.add(createAttackMove(currentX, currentY, newX, newY, squares));
            } else if (targetPlayableSquare.isEmpty()) {
                goMoves.add(new Move(
                        thisPlayableSquare.getPlayedPosition(),
                        targetPlayableSquare.getPlayedPosition(),
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

        Square potentialEnemySquare = squares[newY][newX];
        Square potentialLandingSquare = squares[landingY][landingX];

        return potentialEnemySquare instanceof PlayableSquare enemySquare &&
                !enemySquare.isEmpty() &&
                enemySquare.getPlacedPiece().getColor() != this.color &&
                potentialLandingSquare instanceof PlayableSquare landingSquare &&
                landingSquare.isEmpty();
    }

    // Helper Method: Create an ATTACK move
    private Move createAttackMove(int currentX, int currentY, int enemyX, int enemyY, Square[][] squares) {
        int xShift = enemyX - currentX;
        int yShift = enemyY - currentY;

        int landingX = enemyX + xShift;
        int landingY = enemyY + yShift;

        if(!(squares[landingY][landingX] instanceof PlayableSquare landingSquare))
            throw new PiecePlacedNotOnPlayableSquareException("Landing Square is not a type of PlayableSquare.");

//        if(!(squares[enemyX][enemyY] instanceof PlayableSquare enemySquare))
//            throw new PiecePlacedNotOnPlayableSquareException("Enemy Square is not a type of PlayableSquare.");


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

    public void setSquare(PlayableSquare square) {
        if (!square.isEmpty()) {
            throw new IllegalStateException("Cannot place a piece on an occupied square.");
        }
        this.square = square;
        square.setPiece(this);
    }

    public void moveToSquare(PlayableSquare targetSquare) {
        if (!targetSquare.isEmpty()) {
            throw new IllegalStateException("Target square is already occupied.");
        }
        // Remove from current square
        if (this.square instanceof PlayableSquare currentSquare) {
            currentSquare.removePiece();
        }
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
