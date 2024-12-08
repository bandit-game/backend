package be.kdg.integration5.checkerscontext.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        Game game = new Game(new GameId(UUID.randomUUID()), List.of(new Player("test1", true), new Player("test2", false)));
        game.start();
        board = game.getBoard();
    }


    @Test
    void testPiecesPlacedCorrectly() {
        Square[][] squares = board.getSquares();
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

}