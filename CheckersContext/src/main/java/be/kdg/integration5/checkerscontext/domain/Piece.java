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
        List<Move> regularMoves = new ArrayList<>();

        if (!(square instanceof PlayableSquare playableSquare))
            throw new PiecePlacedNotOnPlayableSquareException("Piece is placed not on a PlayableSquare.");

        PlayedPosition initialPlayedPosition = playableSquare.getPlayedPosition();
        int squareNumber = square.getSquareNumber();
        int x = squareNumber / Board.BOARD_SIZE;
        int y = squareNumber % Board.BOARD_SIZE;

        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] direction : directions)
            addAttackMovesInDirection(attackMoves, initialPlayedPosition, x, y, direction[0], direction[1]);

        if (!attackMoves.isEmpty()) {
            attackMoves.addAll(getPossibleMoves().stream().filter(move -> move.getType() == Move.MoveType.ATTACK).toList());
            return attackMoves;
        }


        for (int[] direction : directions)
            addRegularMovesInDirection(regularMoves, initialPlayedPosition, x, y, direction[0], direction[1]);

        return regularMoves;
    }

    private void addAttackMovesInDirection(List<Move> moves, PlayedPosition initialPlayedPosition, int x, int y, int xChange, int yChange) {
        Square[][] squares = square.getBoard().getSquares();
        int futureX = x + xChange;
        int futureY = y + yChange;

        if (isOutOfBounds(futureX, futureY)) return;

        if (canAttack(futureX, futureY, xChange, yChange, squares)) {
            int attackX = futureX + xChange;
            int attackY = futureY + yChange;
            moves.add(new Move(initialPlayedPosition, ((PlayableSquare) squares[attackY][attackX]).getPlayedPosition(), Move.MoveType.ATTACK));

            // For kings, continue searching for additional attacks in the same direction
            if (isKing) {
                addKingMoves(moves, initialPlayedPosition, attackX, attackY, xChange, yChange);
            }
        }
    }

    private void addRegularMovesInDirection(List<Move> moves, PlayedPosition initialPlayedPosition, int x, int y, int xChange, int yChange) {
        Square[][] squares = square.getBoard().getSquares();
        int futureX = x + xChange;
        int futureY = y + yChange;

        if (isOutOfBounds(futureX, futureY)) return;

        Square targetSquare = squares[futureY][futureX];
        if (!(targetSquare instanceof PlayableSquare nextPlayableSquare)) return;

        // Forward movement restriction for default pieces
        if (!this.isKing && !isMovingForward(yChange)) {
            return;
        }

        if (nextPlayableSquare.isEmpty()) {
            moves.add(new Move(initialPlayedPosition, nextPlayableSquare.getPlayedPosition(), Move.MoveType.GO));

            // For kings, add further moves in the same direction
            if (this.isKing) {
                addKingMoves(moves, initialPlayedPosition, futureX, futureY, xChange, yChange);
            }
        }
    }

    private void addKingMoves(List<Move> moves, PlayedPosition initialPlayedPosition, int startX, int startY, int xChange, int yChange) {
        Square[][] squares = this.square.getBoard().getSquares();
        int currentX = startX + xChange;
        int currentY = startY + yChange;

        while (!isOutOfBounds(currentX, currentY)) {
            Square nextSquare = squares[currentY][currentX];
            if (nextSquare instanceof PlayableSquare playableSquare && playableSquare.isEmpty()) {
                moves.add(new Move(initialPlayedPosition, playableSquare.getPlayedPosition(), Move.MoveType.GO));
                currentX += xChange;
                currentY += yChange;
            } else {
                break;
            }
        }
    }

    private boolean canAttack(int futureX, int futureY, int xChange, int yChange, Square[][] squares) {
        int attackX = futureX + xChange;
        int attackY = futureY + yChange;

        if (isOutOfBounds(attackX, attackY))
            return false;

        Square potentialEnemySquare = squares[futureY][futureX];
        Square potentialLandingSquare = squares[attackY][attackX];

        // Check if the target square contains an enemy piece and the landing square is empty
        return potentialEnemySquare instanceof PlayableSquare enemySquare &&
                !enemySquare.isEmpty() &&
                enemySquare.getPlacedPiece().getColor() != this.color && // Ensure the piece is of a different color
                potentialLandingSquare instanceof PlayableSquare landingSquare &&
                landingSquare.isEmpty();
    }

    private boolean isMovingForward(int yChange) {
        return (this.color == PieceColor.WHITE && yChange > 0) || (this.color == PieceColor.BLACK && yChange < 0);
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
        UP_RIGHT(1, 1),
        UP_LEFT(1, -1),
        DOWN_RIGHT(-1, 1),
        DOWN_LEFT(-1, -1),;

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
