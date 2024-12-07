package be.kdg.integration5.checkerscontext.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    private Game game;
    private Board board;
    private Piece whitePiece;
    private Piece blackPiece;

    @BeforeEach
    void setUp() {
        game = new Game(new GameId(UUID.randomUUID()), List.of(new Player("test1", true), new Player("test2", false)));
        game.start();

        board = game.getBoard();

        // Place white piece at (5, 5)
        Square whiteSquare = board.getSquares()[5][5];
        whitePiece = new Piece(1, whiteSquare, Piece.PieceColor.WHITE);

        // Place black piece at (3, 3)
        Square blackSquare = board.getSquares()[3][3];
        blackPiece = new Piece(2, blackSquare, Piece.PieceColor.BLACK);
    }

    @Test
    void testGetPossibleMoves_GoMove() {
        // Ensure squares (6,4) and (6,6) are empty
        Square goSquare1 = board.getSquares()[6][4];
        Square goSquare2 = board.getSquares()[6][6];
        goSquare1.removePiece();
        goSquare2.removePiece();

        // Get possible moves for the white piece
        List<Move> moves = whitePiece.getPossibleMoves();

        // Verify GO moves
        assertEquals(2, moves.size(), "White piece should have two GO moves (down-left and down-right)");
        for (Move move : moves) {
            assertEquals(Move.MoveType.GO, move.getType());
        }
    }

    @Test
    void testGetPossibleMoves_AttackMove() {
        // Place a black piece diagonally adjacent to the white piece
        Square enemySquare = board.getSquares()[4][4];
        blackPiece.setSquare(enemySquare);

        // Ensure landing square (3,3) is empty
        Square landingSquare = board.getSquares()[3][3];
        landingSquare.setPlacedPiece(null);

        // Get possible moves for the white piece
        List<Move> moves = whitePiece.getPossibleMoves();

        // Verify ATTACK move
        assertEquals(1, moves.size(), "White piece should have one ATTACK move");
        Move attackMove = moves.get(0);
        assertEquals(Move.MoveType.ATTACK, attackMove.getType());
        assertEquals(new PlayedPosition(5, 5), attackMove.getInitialPosition());
        assertEquals(new PlayedPosition(3, 3), attackMove.getFuturePosition());
    }

    @Test
    void testGetPossibleMoves_AttackOverridesGo() {
        // Place a black piece diagonally adjacent to the white piece
        Square enemySquare = board.getSquares()[4][4];
        blackPiece.setSquare(enemySquare);

        // Ensure landing square (3,3) is empty
        Square landingSquare = board.getSquares()[3][3];
        landingSquare.removePiece();

        // Ensure squares for GO moves are also empty
        Square goSquare1 = board.getSquares()[6][4];
        Square goSquare2 = board.getSquares()[6][6];
        goSquare1.removePiece();
        goSquare2.removePiece();

        // Get possible moves for the white piece
        List<Move> moves = whitePiece.getPossibleMoves();

        // Verify only ATTACK moves are returned
        assertEquals(1, moves.size(), "White piece should have one ATTACK move, overriding GO moves");
        assertEquals(Move.MoveType.ATTACK, moves.get(0).getType());
    }

    @Test
    void testGetPossibleMoves_NoMoves() {
        // Surround the white piece with other white pieces
        Square surroundingSquare1 = whitePiece.getSquare().getBoard().getSquares()[4][4];
        surroundingSquare1.setPlacedPiece(new Piece(3, surroundingSquare1, Piece.PieceColor.WHITE));

        Square surroundingSquare2 = whitePiece.getSquare().getBoard().getSquares()[4][6];
        surroundingSquare2.setPlacedPiece(new Piece(4, surroundingSquare2, Piece.PieceColor.WHITE));

        // Get possible moves
        List<Move> moves = whitePiece.getPossibleMoves();

        // Verify no moves are available
        assertTrue(moves.isEmpty(), "White piece should have no valid moves");
    }

    @Test
    void testGetPossibleMoves_KingMoves() {
        // Make the white piece a King
        whitePiece.setKing(true);

        // Place the King in an open area
        Square kingSquare = board.getSquares()[4][4];
        whitePiece.setSquare(kingSquare);

        // Ensure squares (3,3), (3,5), (5,3), and (5,5) are empty
        board.getSquares()[3][3].removePiece();
        board.getSquares()[3][5].removePiece();
        board.getSquares()[5][3].removePiece();
        board.getSquares()[5][5].removePiece();

        // Get possible moves for the King
        List<Move> moves = whitePiece.getPossibleMoves();

        // Verify all diagonal moves are available
        assertEquals(4, moves.size(), "King piece should have four GO moves in all diagonal directions");
    }
}