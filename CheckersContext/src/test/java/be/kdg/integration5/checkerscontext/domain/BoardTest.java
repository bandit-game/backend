package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.domain.exception.MoveNotValidException;
import be.kdg.integration5.checkerscontext.domain.exception.NotPlayablePositionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
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
    void testPiecesPlacedCorrectly() {
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
    void testBoardGeneratedCorrectly() {
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
    void testValidMove() {
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
    void testInvalidMove() {
        Move invalidMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(3, 4),
                Move.MoveType.GO
        );

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), invalidMove));
    }

    @Test
    void testMultipleStepsAttackMove() {
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
    void testOutOfBoundsMove() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Move(
                new PiecePosition(0, 1),
                new PiecePosition(-1, 0),
                Move.MoveType.GO
        ));
    }

    @Test
    void testNotPlayablePosition() {
        assertThrows(NotPlayablePositionException.class, () -> new PiecePosition(0, 0));
        assertThrows(NotPlayablePositionException.class, () -> new PiecePosition(2, 2));
        assertThrows(NotPlayablePositionException.class, () -> new PiecePosition(4, 4));
    }

    @Test
    void testPlayerIsParticipant() {
        assertEquals(board.getCurrentPlayer().getPlayerId(), player1.getPlayerId());
        assertTrue(board.getPlayers().contains(player2));
    }

    @Test
    void testSwitchPlayerAfterMove() {
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
    void testMoveToSamePosition() {
        Move samePositionMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(1, 2),
                Move.MoveType.GO
        );
        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), samePositionMove));
    }

    @Test
    void testInvalidPlayerMove() {
        Move move = new Move(
                new PiecePosition(2, 3),
                new PiecePosition(3, 4),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player2.getPlayerId(), move));
    }

    @Test
    void testOccupiedTargetSquare() {
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
    void testBackwardMoveForNonKing() {
        Move backwardMove = new Move(
                new PiecePosition(3, 4),
                new PiecePosition(2, 5),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), backwardMove));
    }

    @Test
    void testOversteppingSquares() {
        Move oversteppingMove = new Move(
                new PiecePosition(2, 3),
                new PiecePosition(4, 5),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(2, 3), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), oversteppingMove));
    }

    @Test
    void testKingMultiSquareMove() {
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
    void testAttackMoveNoCapturablePiece() {
        Move invalidAttackMove = new Move(
                new PiecePosition(1, 2),
                new PiecePosition(3, 4),
                Move.MoveType.ATTACK
        );
        board.addPiece(new Piece(new PiecePosition(1, 2), Piece.PieceColor.WHITE, player1));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), invalidAttackMove));
    }


    @Test
    void testCrossingMultiplePiecesInGoMove() {
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
    void testMovingOpponentPiece() {
        Move moveOpponentPiece = new Move(
                new PiecePosition(3, 4),
                new PiecePosition(5, 6),
                Move.MoveType.GO
        );
        board.addPiece(new Piece(new PiecePosition(3, 4), Piece.PieceColor.BLACK, player2));

        assertThrows(MoveNotValidException.class, () -> board.movePiece(player1.getPlayerId(), moveOpponentPiece));
    }


}