package be.kdg.integration5.checkerscontext.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PieceTest {
    private Game game;
    private Board board;
    private Piece whitePiece;
    private Piece blackPiece;

    @BeforeEach
    void setUp() {
        game = new Game(new GameId(UUID.randomUUID()), List.of(new Player("test1"), new Player("test2")));
        game.start();

        board = game.getBoard();

        whitePiece = board.getSquares()[6][5].getPlacedPiece();

        blackPiece = board.getSquares()[3][4].getPlacedPiece();
    }
/*
    @Test
    void testGetPossibleMoves_GoMove() {
        Square goSquare1 = board.getSquares()[4][3];
        Square goSquare2 = board.getSquares()[4][5];
        goSquare1.removePiece();
        goSquare2.removePiece();

        List<Move> moves = blackPiece.getPossibleMoves();

        assertEquals(2, moves.size(), "Black piece should have two GO moves (down-left and down-right)");
        for (Move move : moves) {
            assertEquals(Move.MoveType.GO, move.getType());
        }
    }

    @Test
    void testGetPossibleMoves_AttackMove() {
        Square enemySquare = board.getSquares()[4][3];
        whitePiece.setSquare(enemySquare);

        Square landingSquare = board.getSquares()[5][2];
        landingSquare.removePiece();

        List<Move> moves = blackPiece.getPossibleMoves();

        assertEquals(1, moves.size(), "Black piece should have one ATTACK move");
        Move attackMove = moves.get(0);
        assertEquals(Move.MoveType.ATTACK, attackMove.getType());
        assertEquals(new PiecePosition(4, 3), attackMove.getInitialPosition());
        assertEquals(new PiecePosition(2, 5), attackMove.getFuturePosition());
    }

    @Test
    void testGetPossibleMoves_AttackOverridesGo() {
        Square enemySquare = board.getSquares()[4][3];
        whitePiece.setSquare(enemySquare);

        Square landingSquare = board.getSquares()[5][2];
        landingSquare.removePiece();

        Square goSquare = board.getSquares()[4][5];
        goSquare.removePiece();

        List<Move> moves = blackPiece.getPossibleMoves();

        assertEquals(1, moves.size(), "Black piece should have one ATTACK move, overriding GO moves");
        assertEquals(Move.MoveType.ATTACK, moves.get(0).getType());
    }

    @Test
    void testGetPossibleMoves_NoMoves() {
        Square surroundingSquare1 = whitePiece.getSquare().getBoard().getSquares()[4][3];
        new Piece(3, surroundingSquare1, Piece.PieceColor.BLACK);

        Square surroundingSquare2 = whitePiece.getSquare().getBoard().getSquares()[4][5];
        new Piece(4, surroundingSquare2, Piece.PieceColor.BLACK);

        List<Move> moves = blackPiece.getPossibleMoves();

        assertTrue(moves.isEmpty(), "White piece should have no valid moves");
    }

    @Test
    void testGetPossibleMoves_KingMoves() {
        whitePiece.setKing(true);

        Square kingSquare = board.getSquares()[5][4];
        whitePiece.setSquare(kingSquare);

        board.getSquares()[4][3].removePiece();
        board.getSquares()[4][5].removePiece();
        board.getSquares()[6][3].removePiece();
        board.getSquares()[6][5].removePiece();

        List<Move> moves = whitePiece.getPossibleMoves();

        assertEquals(4, moves.size(), "King piece should have four GO moves in all diagonal directions");
    }*/
}