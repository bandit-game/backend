package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.domain.exception.MoveNotValidException;
import be.kdg.integration5.checkerscontext.domain.exception.NotPlayablePositionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class BoardTest {
    private Game game;
    private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        player1 = new Player(new PlayerId(UUID.randomUUID()), "Alice");
        player2 = new Player(new PlayerId(UUID.randomUUID()), "Bob");
        List<Player> players = List.of(player1, player2);

        List<Piece> pieces = new ArrayList<>();

        Board newBoard = new Board(pieces, players, player1);
        game = new Game(new GameId(UUID.randomUUID()), newBoard, players);
        board = game.getBoard();
    }



    @Test
    void shouldPlacePiecesCorrectlyOnBoardStart() {
        game.start();
        Square[][] squares = game.getBoard().getSquares();
        Square testSquare = null;

        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            for (int x = 0; x < Board.BOARD_SIZE; x++) {
                testSquare = squares[y][x];
                assertNotNull(testSquare);
                if((y + x) % 2 == 1 && y * 10 + x < Board.PIECES_PER_PLAYER*2) {
                    assertNotNull(testSquare.getPlacedPiece());
                    assertEquals(Piece.PieceColor.BLACK, testSquare.getPlacedPiece().getColor());
                } else if ((y + x) % 2 == 1 && y * 10 + x > Board.BOARD_SIZE * Board.BOARD_SIZE - Board.PIECES_PER_PLAYER * 2) {
                    assertNotNull(testSquare.getPlacedPiece());
                    assertEquals(Piece.PieceColor.WHITE, testSquare.getPlacedPiece().getColor());
                } else {
                    assertNull(testSquare.getPlacedPiece());
                }
            }
        }
    }

    @Test
    void shouldGenerateBoardWithCorrectDimensionsAndNonNullSquares() {
        Square[][] squares = board.getSquares();
        assertEquals(Board.BOARD_SIZE, squares.length);
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            assertEquals(Board.BOARD_SIZE, squares[i].length);
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                assertNotNull(squares[i][j]);
            }
        }
    }

    @Test
    void shouldReturnTwoPossibleNormalMovesForNonKingPiece() {
        Piece piece = new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player1);
        board.addPiece(piece);

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(4, 5), Move.MoveType.GO)));
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(2, 5), Move.MoveType.GO)));
    }

    @Test
    void shouldReturnSingleAttackMoveWhenEnemyPieceIsAdjacent() {
        Piece piece = new Piece(new PiecePosition(3, 4), Piece.PieceColor.WHITE, player1);
        board.addPiece(piece);
        board.addPiece(new Piece(new PiecePosition(4, 5), Piece.PieceColor.BLACK, player2));

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(1, possibleMoves.size());
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(5, 6), Move.MoveType.ATTACK)));
    }

    @Test
    void shouldReturnSingleMultiStepAttackMoveWithIntermediatePositions() {
        Piece piece = new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1);
        board.addPiece(piece);
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(5, 6), Piece.PieceColor.BLACK, player2));

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(1, possibleMoves.size());
        Move expectedMove = new Move(new PiecePosition(2, 3), new PiecePosition(6, 7), Move.MoveType.ATTACK);
        expectedMove.addIntermediateAttackPosition(new PiecePosition(4, 5));
        assertTrue(possibleMoves.contains(expectedMove));
    }

    @Test
    void shouldAllowKingToMoveInAllDirections() {
        Piece king = new Piece(new PiecePosition(3, 4), Piece.PieceColor.WHITE, player1);
        king.setKing(true);
        board.addPiece(king);

        List<Move> possibleMoves = board.getPossibleMoves(king);
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(5, 6), Move.MoveType.GO)));
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(1, 2), Move.MoveType.GO)));
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(5, 2), Move.MoveType.GO)));
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(3, 4), new PiecePosition(1, 6), Move.MoveType.GO)));
    }

    @Test
    void shouldReturnTwoGOMovesForwardWhenHaveEnemyPieceAdjacentButNotPossibleToAttack() {
        Piece piece = new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1);
        board.addPiece(piece);
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(4, 5), Piece.PieceColor.BLACK, player2));

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(2, possibleMoves.size());
        Move expectedMove1 = new Move(new PiecePosition(2, 3), new PiecePosition(1, 2), Move.MoveType.GO);
        Move expectedMove2 = new Move(new PiecePosition(2, 3), new PiecePosition(3, 2), Move.MoveType.GO);
        assertTrue(possibleMoves.contains(expectedMove1));
        assertTrue(possibleMoves.contains(expectedMove2));
    }

    @Test
    void shouldReturnTwoAttackMovesOfTheSameMoveLengthWhenHaveTwoEnemyPieceAdjacentAndPossibleToAttack() {
        Piece piece = new Piece(new PiecePosition(2, 5), Piece.PieceColor.WHITE, player1);
        board.addPiece(piece);
        board.addPiece(new Piece(new PiecePosition(1, 4), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(2, possibleMoves.size());
        Move expectedMove1 = new Move(new PiecePosition(2, 5), new PiecePosition(0, 3), Move.MoveType.ATTACK);
        Move expectedMove2 = new Move(new PiecePosition(2, 5), new PiecePosition(4, 3), Move.MoveType.ATTACK);
        assertTrue(possibleMoves.contains(expectedMove1));
        assertTrue(possibleMoves.contains(expectedMove2));
    }

    @Test
    void shouldReturnOneMoveWhenPieceIsAtCornerOfBoard() {
        Piece piece = new Piece(new PiecePosition(9, 0), Piece.PieceColor.BLACK, player1);
        board.addPiece(piece);

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(1, possibleMoves.size());
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(9, 0), new PiecePosition(8, 1), Move.MoveType.GO)));
    }

    @Test
    void shouldReturnNoMovesWhenPieceIsBlockedByAlliedPieces() {
        Piece piece = new Piece(new PiecePosition(4, 3), Piece.PieceColor.WHITE, player1);
        board.addPiece(piece);
        board.addPiece(new Piece(new PiecePosition(3, 2), Piece.PieceColor.WHITE, player1));
        board.addPiece(new Piece(new PiecePosition(5, 2), Piece.PieceColor.WHITE, player1));

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void shouldReturnAllPossibleAttackMovesWhenSurroundedByEnemies() {
        PiecePosition piecePosition = new PiecePosition(3, 4);
        Piece piece = new Piece(piecePosition, Piece.PieceColor.WHITE, player1);
        board.addPiece(piece);
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(2, 5), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(4, 3), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(4, 5), Piece.PieceColor.BLACK, player2));

        List<Move> possibleMoves = board.getPossibleMoves(piece);
        assertEquals(4, possibleMoves.size());
        assertTrue(possibleMoves.stream().allMatch(move -> move.getType() == Move.MoveType.ATTACK));
        assertTrue(possibleMoves.stream().allMatch(move -> move.getMoveLength() == 2));
        assertTrue(possibleMoves.stream().allMatch(move -> move.getInitialPosition().equals(piecePosition)));
    }

    @Test
    void shouldAllowKingToMoveFreelyNearEdge() {
        Piece king = new Piece(new PiecePosition(0, 1), Piece.PieceColor.WHITE, player1);
        king.setKing(true);
        board.addPiece(king);

        List<Move> possibleMoves = board.getPossibleMoves(king);

        assertEquals(9, possibleMoves.size());
        assertTrue(possibleMoves.contains(new Move(new PiecePosition(0, 1), new PiecePosition(1, 0), Move.MoveType.GO)));
        for (int x = king.getPiecePosition().x() + 1, y = king.getPiecePosition().y() + 1; x >= 0 && x < Board.BOARD_SIZE && y >= 0 && y < Board.BOARD_SIZE; x++, y++) {
            assertTrue(possibleMoves.contains(new Move(new PiecePosition(0, 1), new PiecePosition(x, y), Move.MoveType.GO)));
        }
        assertTrue(possibleMoves.stream().allMatch(move -> move.getMoveLength() == 2));
    }



    @Test
    void shouldNotThrowExceptionForValidGoMove() {
        Move move = new Move(
                new PiecePosition(2, 3),
                new PiecePosition(1, 2),
                Move.MoveType.GO
        );

        board.removePiece(1, 2);
        board.removePiece(2, 3);
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1));

        assertDoesNotThrow(() -> board.movePiece(player1.getPlayerId(), move));
    }

    @Test
    void shouldThrowExceptionForInvalidMove() {
        Move invalidMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(3, 4),
                Move.MoveType.GO
        );

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), invalidMove));
    }

    @Test
    void shouldExecuteMultipleStepAttackMoveSuccessfully() {
        Move attackMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(1, 6),
                Move.MoveType.ATTACK
        );
        attackMove.addIntermediateAttackPosition(new PiecePosition(3, 4));

        board.removePiece(3, 4);
        board.removePiece(1, 6);
        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.WHITE, player1));
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.BLACK, player2));
        board.addPiece(new Piece(new PiecePosition(2, 5), Piece.PieceColor.BLACK, player2));

        board.movePiece(player1.getPlayerId(), attackMove);

        assertNull(board.getSquares()[4][3].getPlacedPiece());
        assertNull(board.getSquares()[2][1].getPlacedPiece());

        assertNull(board.getSquares()[5][2].getPlacedPiece());
        assertNull(board.getSquares()[3][2].getPlacedPiece());

        assertNotNull(board.getSquares()[6][1].getPlacedPiece());
    }

    @Test
    void shouldThrowExceptionForOutOfBoundsMove() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Move(
                new PiecePosition(0, 1),
                new PiecePosition(-1, 0),
                Move.MoveType.GO
        ));
    }

    @Test
    void shouldThrowExceptionForMoveToNotPlayablePosition() {
        assertThrows(NotPlayablePositionException.class, () -> new PiecePosition(0, 0));
        assertThrows(NotPlayablePositionException.class, () -> new PiecePosition(2, 2));
        assertThrows(NotPlayablePositionException.class, () -> new PiecePosition(4, 4));
    }

    @Test
    void shouldValidatePlayerParticipationInGame() {
        assertEquals(board.getCurrentPlayer().getPlayerId(), player1.getPlayerId());
        assertTrue(board.getPlayers().contains(player2));
    }

    @Test
    void shouldSwitchPlayerAfterValidGoMove() {
        Move move = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(2, 3),
                Move.MoveType.GO
        );

        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.BLACK, player1));
        board.movePiece(player1.getPlayerId(), move);

        // Assuming the game logic switches players after a valid move
        assertEquals(player2, board.getCurrentPlayer());
    }

    @Test
    void shouldSwitchPlayerAfterValidAttackMove() {
        Move move = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(3, 4),
                Move.MoveType.ATTACK
        );

        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.BLACK, player1));
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player2));
        board.movePiece(player1.getPlayerId(), move);

        // Assuming the game logic switches players after a valid move
        assertEquals(player2, board.getCurrentPlayer());
    }


    @Test
    void shouldThrowExceptionForMoveToSamePosition() {
        Move samePositionMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(1, 2),
                Move.MoveType.GO
        );
        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), samePositionMove));
    }

    @Test
    void shouldThrowExceptionForMoveByWrongPlayer() {
        Move move = new Move(
                new PiecePosition(2, 3),
                new PiecePosition(3, 4),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player2.getPlayerId(), move));
    }

    @Test
    void shouldThrowExceptionForMoveToOccupiedSquare() {
        Move move = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(3, 4),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.WHITE, player1));
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), move));
    }

    @Test
    void shouldThrowExceptionForBackwardMoveByNonKing() {
        Move backwardMove = new Move(
                new PiecePosition(3, 4),
                new PiecePosition(2, 5),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), backwardMove));
    }

    @Test
    void shouldThrowExceptionForOversteppingInGoMove() {
        Move oversteppingMove = new Move(
                new PiecePosition(2, 3),
                new PiecePosition(4, 5),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), oversteppingMove));
    }

    @Test
    void shouldAllowKingToMoveMultipleSquares() {
        Move kingMove = new Move(
                new PiecePosition(2, 3),
                new PiecePosition(4, 5),
                Move.MoveType.GO
        );
        Piece king = new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1);
        king.setKing(true);
        board.addPiece(king);

        assertDoesNotThrow(() -> board.movePiece(player1.getPlayerId(), kingMove));
    }

    @Test
    void shouldThrowExceptionForAttackMoveWithNoCapturablePiece() {
        Move invalidAttackMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(3, 4),
                Move.MoveType.ATTACK
        );
        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), invalidAttackMove));
    }


    @Test
    void shouldThrowExceptionForCrossingMultiplePiecesInGoMove() {
        Move invalidGoMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(5, 6),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.WHITE, player1));
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), invalidGoMove));
    }

    @Test
    void shouldThrowExceptionForMovingOpponentPiece() {
        Move moveOpponentPiece = new Move(
                new PiecePosition(3, 4),
                new PiecePosition(5, 6),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), moveOpponentPiece));
    }
}