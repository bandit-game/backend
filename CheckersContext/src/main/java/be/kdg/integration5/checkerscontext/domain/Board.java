package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.domain.exception.DirectionSearchException;
import be.kdg.integration5.checkerscontext.domain.exception.MoveNotValidException;
import be.kdg.integration5.checkerscontext.domain.exception.NotPossibleToPlacePieceException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Getter
@Setter
@ToString
public class Board {
    public static final int BOARD_SIZE = 10;
    public static final int PIECES_PER_PLAYER = 20;

    private Player currentPlayer;

    private final Square[][] squares;
    private final List<Piece> pieces;
    private final List<Player> players;

    {
        this.pieces = new ArrayList<>();
        this.squares = new Square[BOARD_SIZE][BOARD_SIZE];
        generateBoard();
    }

    public Board(List<Player> players) {
        this.players = Collections.unmodifiableList(players);
    }

    public Board(List<Piece> pieces, List<Player> players, Player currentPlayer) {
        this.pieces.addAll(pieces);
        this.players = players;
        this.currentPlayer = currentPlayer;
        updateBoard();
    }

    public void setUpNewBoard(Player firstPlayer) {
        setCurrentPlayer(firstPlayer);
        createPieces(firstPlayer);
        placePieces();
    }

    private void createPieces(Player firstPlayer) {
        Player secondPlayer = players.stream().filter(player -> player.equals(firstPlayer)).findFirst().orElseThrow(
                () -> new IllegalStateException("Second Player not found")
        );

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if ((y + x) % 2 == 1) {
                    if (y * 10 + x < PIECES_PER_PLAYER * 2)
                        pieces.add(new Piece(new PiecePosition(x, y), Piece.PieceColor.BLACK, secondPlayer));
                    else if (y * 10 + x > BOARD_SIZE * BOARD_SIZE - PIECES_PER_PLAYER * 2)
                        pieces.add(new Piece(new PiecePosition(x, y), Piece.PieceColor.WHITE, firstPlayer));
                }
            }
        }
    }

    private void updateBoard() {
        removeAllPieces();
        placePieces();
    }

    public void addPiece(Piece piece) {
        if (piece == null)
            return;

        int x = piece.getPiecePosition().x();
        int y = piece.getPiecePosition().y();
        Square square = this.squares[y][x];

        if (!square.isEmpty())
            throw new NotPossibleToPlacePieceException("Target square is occupied. [x: %s, y: %s]".formatted(x, y));

        if (pieces.contains(piece))
            throw new NotPossibleToPlacePieceException("Piece is already placed on the board.");

        this.pieces.add(piece);
        square.setPlacedPiece(piece);
    }

    public void removePiece(int x, int y) {
        this.squares[y][x].removePiece();
        this.pieces.removeIf(piece -> piece.getPiecePosition().x() == x && piece.getPiecePosition().y() == y);
    }

    public void removeAllPieces() {
        for (Square[] row : squares)
            for (Square square : row)
                square.removePiece();
    }

    private void placePieces() {
        for (Piece piece : pieces) {
            int x = piece.getPiecePosition().x();
            int y = piece.getPiecePosition().y();
            squares[y][x].setPlacedPiece(piece);
        }
    }

    private void generateBoard() {
        for (int y = 0; y < BOARD_SIZE; y++)
            for (int x = 0; x < BOARD_SIZE; x++)
                squares[y][x] = new Square(x, y);
    }

    public List<Move> getPossibleMoves(Piece piece) {
        List<Move> attackMoves = new ArrayList<>();
        List<Move> goMoves = new ArrayList<>();

        int currentX = piece.getPiecePosition().x();
        int currentY = piece.getPiecePosition().y();
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
                        new PiecePosition(currentX, currentY),
                        new PiecePosition(targetX, targetY),
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
                new PiecePosition(currentX, currentY),
                new PiecePosition(landingX, landingY),
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

    public void movePiece(PlayerId moverId, Move move) {
        validateMove(moverId, move);

        changePieceLocation(move);
        if (move.getType() == Move.MoveType.ATTACK)
            removeCapturedPieces(move);

        updateBoard();
        switchCurrentPlayer();
    }

    private void switchCurrentPlayer() {
        this.currentPlayer = players.stream().filter(player -> !player.equals(currentPlayer)).findFirst().orElseThrow(
                () -> new IllegalStateException("Not enough players.")
        );
    }


    private void changePieceLocation(Move move) {
        PiecePosition oldPosition = move.getInitialPosition();
        PiecePosition newPosition = move.getFuturePosition();
        Square oldSquare = squares[oldPosition.y()][oldPosition.x()];
        Square newSquare = squares[newPosition.y()][newPosition.x()];

        newSquare.setPlacedPiece(oldSquare.getPlacedPiece());
        oldSquare.removePiece();
    }

    private void removeCapturedPieces(Move move) {
        PiecePosition previousPosition = move.getInitialPosition();
        for (PiecePosition intermediateAttackPosition : move.getAllAttackSteps()) {
            removeAllPiecesInBetween(previousPosition, intermediateAttackPosition);
            previousPosition = intermediateAttackPosition;
        }
    }

    private void removeAllPiecesInBetween(PiecePosition startPosition, PiecePosition endPosition) {
        MoveDirection direction = findMoveDirection(startPosition, endPosition);
        for (int x = startPosition.x() + direction.xShift, y = startPosition.y() + direction.yShift; x != endPosition.x() && y != endPosition.y(); x += direction.xShift, y += direction.yShift) {
            Square targetSquare = squares[y][x];
            if (!targetSquare.isEmpty())
                removePiece(x, y);
        }
    }

    private void validateMove(PlayerId moverId, Move move) {
        int currentX = move.getInitialPosition().x();
        int currentY = move.getInitialPosition().y();
        int targetX = move.getFuturePosition().x();
        int targetY = move.getFuturePosition().y();

        if (currentX == targetX && currentY == targetY)
            throw new MoveNotValidException("Moving to the same location is not possible.", moverId);

        if (isOutOfBounds(targetX, targetY) || isOutOfBounds(currentX, currentY))
            throw new MoveNotValidException("Move positions can not be less than 0 and greater than " + BOARD_SIZE, moverId);


        Piece piece = squares[currentY][currentX].getPlacedPiece();
        if (piece == null)
            throw new MoveNotValidException("No piece is placed on this square.", moverId);

        PlayerId ownerId = piece.getOwner().getPlayerId();
        if (!moverId.equals(ownerId))
            throw new MoveNotValidException("Only owner is allowed to move the piece.", moverId);

        Square targetSquare = squares[targetY][targetX];
        if (!targetSquare.isEmpty())
            throw new MoveNotValidException("Moving to the occupied square is not allowed.", moverId);


        Move.MoveType moveType = move.getType();
        boolean isKing = piece.isKing();
        if (moveType == Move.MoveType.GO) {
            if (!isMovingForward(targetY - currentY, piece.getColor()) && !isKing)
                throw new MoveNotValidException("Non king piece can not move backwards.", moverId);

            int distance = calculateDistance(currentX, currentY, targetX, targetY);
            if (distance != 1 && !isKing)
                throw new MoveNotValidException("Non king piece can not step more than 1 square away.", moverId);

            if (calculateLongestDiagonalPiecesSequenceInBetween(move.getInitialPosition(), move.getFuturePosition()) != 0)
                throw new MoveNotValidException("GO move should be crossing other pieces.", moverId);

        } else if (moveType == Move.MoveType.ATTACK) {
            List<PiecePosition> attackPositions = move.getAllAttackSteps();
            PiecePosition previousPosition = move.getInitialPosition();
            for (PiecePosition intermediateAttackPosition : attackPositions) {
                int intermediateX = intermediateAttackPosition.x();
                int intermediateY = intermediateAttackPosition.y();

                if (!squares[intermediateY][intermediateX].isEmpty())
                    throw new MoveNotValidException("All attack steps should be empty.", moverId);

                int distance = calculateDistance(previousPosition.x(), previousPosition.y(), intermediateX, intermediateY);
                if (distance != 2 && !isKing)
                    throw new MoveNotValidException("Non king piece can attack only over one square.", moverId);

                int piecesSequenceInBetween = calculateLongestDiagonalPiecesSequenceInBetween(previousPosition, intermediateAttackPosition);
                if (piecesSequenceInBetween > 1)
                    throw new MoveNotValidException("ATTACK move can not take two or more piece next to each other.", moverId);
                else if (piecesSequenceInBetween < 1)
                    throw new MoveNotValidException("ATTACK move can be performed only if there is a piece to attack.", moverId);


                previousPosition = intermediateAttackPosition;
            }
        }
    }

    private int calculateLongestDiagonalPiecesSequenceInBetween(PiecePosition startPosition, PiecePosition endPosition) {
        MoveDirection direction = findMoveDirection(startPosition, endPosition);
        int piecesSequenceCounter = 0;
        for (int x = startPosition.x() + direction.xShift, y = startPosition.y() + direction.yShift;
             x != endPosition.x() && y != endPosition.y();
             x += direction.xShift, y += direction.yShift) {
            Square targetSquare = squares[y][x];
            if (!targetSquare.isEmpty())
                piecesSequenceCounter++;
            else
                piecesSequenceCounter = 0;
        }
        return piecesSequenceCounter;
    }

    private MoveDirection findMoveDirection(PiecePosition currentPosition, PiecePosition targetPosition) {
        int deltaX = targetPosition.x() - currentPosition.x();
        int deltaY = targetPosition.y() - currentPosition.y();

        if (!isMoveDiagonal(deltaY, deltaX))
            throw new IllegalArgumentException("Absolute delta Y does not match absolute delta X, move is not diagonal.");

        for (MoveDirection direction : MoveDirection.values())
            if (deltaX * direction.xShift > 0 && deltaY * direction.yShift > 0)
                return direction;

        throw new DirectionSearchException("Couldn't find corresponding move direction.");
    }

    private boolean isMoveDiagonal(int deltaY, int deltaX) {
        return Math.abs(deltaY) == Math.abs(deltaX);
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
