package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.domain.exception.DirectionSearchException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Board {
    public static final int BOARD_SIZE = 10;
    public static final int PIECES_PER_PLAYER = 20;

    private Square[][] squares;
    private Player currentPlayer;
    private List<Piece> pieces;

    {
        this.squares = new Square[BOARD_SIZE][BOARD_SIZE];
        generateBoard();
    }

    public void setUpNewBoard(Player firstPlayer, Player secondPlayer) {
        setCurrentPlayer(firstPlayer);
        createPieces(firstPlayer, secondPlayer);
        updatePieces();
    }

    private void createPieces(Player firstPlayer, Player secondPlayer) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if ((y + x) % 2 == 1) {
                    if (y * 10 + x < PIECES_PER_PLAYER * 2)
                        pieces.add(new Piece(x, y, Piece.PieceColor.BLACK, secondPlayer));
                    else if (y * 10 + x > BOARD_SIZE * BOARD_SIZE - PIECES_PER_PLAYER * 2)
                        pieces.add(new Piece(x, y, Piece.PieceColor.WHITE, firstPlayer));
                }
            }
        }
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
        updatePieces();
    }

    private void updatePieces() {
        for (Piece piece : pieces) {
            int x = piece.getCurrentX();
            int y = piece.getCurrentY();
            squares[y][x].setPlacedPiece(piece);
        }
    }

    private void generateBoard() {
        for (int y = 0; y < BOARD_SIZE; y++)
            for (int x = 0; x < BOARD_SIZE; x++)
                squares[y][x] = new Square();
    }

    public List<Move> getPossibleMoves(Piece piece) {
        List<Move> attackMoves = new ArrayList<>();
        List<Move> goMoves = new ArrayList<>();

        int currentX = piece.getCurrentX();
        int currentY = piece.getCurrentY();
        Piece.PieceColor pieceColor = piece.getColor();

        for (MoveDirection direction : MoveDirection.values()) {
            int targetX = currentX + direction.xShift;
            int targetY = currentY + direction.yShift;

            if (!piece.isKing() && !isMovingForward(direction.yShift, pieceColor)) continue;

            if (isOutOfBounds(targetX, targetY)) continue;

            Square targetSquare = squares[targetY][targetX];

            if (canAttack(targetX, targetY, direction, pieceColor)) {
                attackMoves.add(createAttackMove(currentX, currentY, targetX, targetY));
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

    private boolean canAttack(int newX, int newY, MoveDirection direction, Piece.PieceColor pieceColor) {
        int landingX = newX + direction.xShift;
        int landingY = newY + direction.yShift;

        if (isOutOfBounds(landingX, landingY)) return false;

        Square enemySquare = squares[newY][newX];
        Square landingSquare = squares[landingY][landingX];

        return !enemySquare.isEmpty() &&
                enemySquare.getPlacedPiece().getColor() != pieceColor &&
                landingSquare.isEmpty();
    }

    private Move createAttackMove(int currentX, int currentY, int enemyX, int enemyY) {
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


    private boolean isMovingForward(int yChange, Piece.PieceColor pieceColor) {
        return (pieceColor == Piece.PieceColor.WHITE && yChange < 0) || (pieceColor == Piece.PieceColor.BLACK && yChange > 0);
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= Board.BOARD_SIZE || y < 0 || y >= Board.BOARD_SIZE;
    }

    private int calculateDistance(int x, int y, int targetX, int targetY) {
        return Math.max(Math.abs(targetX - x), Math.abs(targetY - y));
    }

    public void movePiece(PlayerId moverId, int currentX, int currentY, int targetX, int targetY) {
        if (validateMove(moverId, currentX, currentY, targetX, targetY))
            return;
    }

    private boolean validateMove(PlayerId moverId, int currentX, int currentY, int targetX, int targetY) {
        if (currentX == targetX && currentY == targetY) return false;
        if (isOutOfBounds(targetX, targetY) || isOutOfBounds(currentX, currentY)) return false;

        Piece piece = squares[currentY][currentX].getPlacedPiece();
        if (piece == null) return false;

        PlayerId ownerId = piece.getOwner().getPlayerId();
        if (!moverId.equals(ownerId))
            return false;

        Square targetSquare = squares[targetY][targetX];
        if (!targetSquare.isEmpty()) return false;

        int distance = calculateDistance(currentX, currentY, targetX, targetY);
        if (distance != 1 && !piece.isKing()) return false;

        return calculateLongestDiagonalPiecesSequenceInBetween(currentX, currentY, targetX, targetY) <= 1;
    }

    private int calculateLongestDiagonalPiecesSequenceInBetween(int currentX, int currentY, int targetX, int targetY) {
        MoveDirection direction = findMoveDirection(currentX, currentY, targetX, targetY);
        int piecesSequenceCounter = 0;
        for (int x = currentX, y = currentY; x != targetX && y != targetY; x += direction.xShift, y += direction.yShift) {
            Square targetSquare = squares[y][x];
            if (!targetSquare.isEmpty())
                piecesSequenceCounter++;
            else
                piecesSequenceCounter = 0;
        }
        return piecesSequenceCounter;
    }

    private MoveDirection findMoveDirection(int currentX, int currentY, int targetX, int targetY) {
        int deltaX = targetX - currentX;
        int deltaY = targetY - currentY;

        if (deltaY != deltaX)
            throw new IllegalArgumentException("deltaY does not match deltaX, move is not diagonal.");

        for (MoveDirection direction : MoveDirection.values())
            if (deltaX * direction.xShift > 0 && deltaY * direction.yShift > 0)
                return direction;

        throw new DirectionSearchException("Couldn't find corresponding move direction.");
    }


    private enum MoveDirection {
        UP_RIGHT(1, -1),
        UP_LEFT(-1, -1),
        DOWN_RIGHT(1, 1),
        DOWN_LEFT(-1, 1);

        final int xShift;
        final int yShift;

        MoveDirection(int xShift, int yShift) {
            this.xShift = xShift;
            this.yShift = yShift;
        }
    }
}
