package be.kdg.integration5.checkerscontext.domain;

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

            if (canAttack(targetX, targetY, direction.xShift, direction.yShift, pieceColor, squares)) {
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

    private boolean canAttack(int newX, int newY, int xShift, int yShift, Piece.PieceColor pieceColor, Square[][] squares) {
        int landingX = newX + xShift;
        int landingY = newY + yShift;

        if (isOutOfBounds(landingX, landingY)) return false;

        Square enemySquare = squares[newY][newX];
        Square landingSquare = squares[landingY][landingX];

        return !enemySquare.isEmpty() &&
                enemySquare.getPlacedPiece().getColor() != pieceColor &&
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


    private boolean isMovingForward(int yChange, Piece.PieceColor pieceColor) {
        return (pieceColor == Piece.PieceColor.WHITE && yChange < 0) || (pieceColor == Piece.PieceColor.BLACK && yChange > 0);
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= Board.BOARD_SIZE || y < 0 || y >= Board.BOARD_SIZE;
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
}
