package be.kdg.integration5.checkerscontext.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PieceTest {
    private Board mockBoard;
    private Square[][] mockSquares;
    private PlayableSquare mockSquare;
    private Piece whitePiece;
    private Piece blackPiece;

    @BeforeEach
    void setUp() {
        // Create a mock board and initialize squares
        mockBoard = mock(Board.class);
        mockSquares = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0))
                    mockSquares[i][j] = mock(PlayableSquare.class);
                else
                    mockSquares[i][j] = mock(VoidSquare.class);
            }
        }
        when(mockBoard.getSquares()).thenReturn(mockSquares);

//        for (int i = 0, j = 0; i < Board.PIECES_PER_PLAYER*2; i+=2, j++) {
//            if(mockSquares[i%10][i+1] instanceof PlayableSquare playableSquare) {
//                playableSquare.setPlacedPiece(new Piece(j, playableSquare, Piece.PieceColor.BLACK));
//
//            }
//            if(mockSquares[(Board.BOARD_SIZE - 1) - i%10][(Board.BOARD_SIZE - 1) - i] instanceof PlayableSquare playableSquare) {
//                playableSquare.setPlacedPiece(new Piece(j, playableSquare, Piece.PieceColor.WHITE));
//            }
//        }

        // Create a mock playable square and set it as the initial position
        mockSquare = mock(PlayableSquare.class);
        when(mockSquare.getBoard()).thenReturn(mockBoard);
        when(mockSquare.getSquareNumber()).thenReturn(18);
        when(mockSquare.isEmpty()).thenReturn(true);

        // Create a white and a black piece
        whitePiece = new Piece(1, mockSquare, Piece.PieceColor.WHITE);
        blackPiece = new Piece(2, mockSquare, Piece.PieceColor.BLACK);
    }

    @Test
    void testNoMovesWhenSurroundedByOccupiedSquares() {
        // Set up all adjacent squares as occupied
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (mockSquares[i][j] instanceof PlayableSquare playableSquare) {
                    when(playableSquare.isEmpty()).thenReturn(false);
                }
            }
        }

        List<Move> moves = whitePiece.getPossibleMoves();
        assertTrue(moves.isEmpty(), "Piece surrounded by occupied squares should have no moves");
    }

    @Test
    void testSingleForwardMoveForNonKing() {
        // Set up an empty playable square in a forward direction
        PlayableSquare forwardSquare = mock(PlayableSquare.class);
        when(forwardSquare.isEmpty()).thenReturn(true);
        when(forwardSquare.getPlayedPosition()).thenReturn(new PlayedPosition(1));

        when(mockSquares[3][3]).thenReturn(forwardSquare);
        when(mockSquares[3][3] instanceof PlayableSquare).thenReturn(true);

        List<Move> moves = whitePiece.getPossibleMoves();
        assertEquals(1, moves.size(), "Non-king should have one possible forward move");
        assertEquals(Move.MoveType.GO, moves.get(0).getMoveType(), "Move should be a GO move");
    }

    @Test
    void testAttackMoveOverEnemyPiece() {
        // Set up an enemy piece diagonally and a landing square after it
        PlayableSquare enemySquare = mock(PlayableSquare.class);
        PlayableSquare landingSquare = mock(PlayableSquare.class);

        Piece enemyPiece = new Piece(3, enemySquare, Piece.PieceColor.BLACK);

        when(enemySquare.isEmpty()).thenReturn(false);
        when(enemySquare.getPiece()).thenReturn(enemyPiece);

        when(landingSquare.isEmpty()).thenReturn(true);
        when(landingSquare.getPlayedPosition()).thenReturn(new PlayedPosition(2, 2));

        // Place the squares on the board
        when(mockSquares[3][3]).thenReturn(enemySquare);
        when(mockSquares[4][4]).thenReturn(landingSquare);

        // Ensure these squares are playable
        when(mockSquares[3][3] instanceof PlayableSquare).thenReturn(true);
        when(mockSquares[4][4] instanceof PlayableSquare).thenReturn(true);

        List<Move> moves = whitePiece.getPossibleMoves();
        assertEquals(1, moves.size(), "Piece should have one attack move");
        assertEquals(Move.MoveType.ATTACK, moves.get(0).getMoveType(), "Move should be an ATTACK move");
    }

    @Test
    void testKingMovesInAllDiagonalDirections() {
        // Set the piece as a king
        whitePiece.setKing(true);

        // Set up empty squares in diagonal directions
        for (int i = 1; i < 4; i++) {
            PlayableSquare diagonalSquare = mock(PlayableSquare.class);
            when(diagonalSquare.isEmpty()).thenReturn(true);
            when(diagonalSquare.getPlayedPosition()).thenReturn(new PlayedPosition(i, i));
            mockSquares[i][i] = diagonalSquare;
        }

        List<Move> moves = whitePiece.getPossibleMoves();
        assertEquals(3, moves.size(), "King should have three possible diagonal moves");
    }

    @Test
    void testNonKingCannotMoveBackward() {
        // Set up a backward square as playable
        PlayableSquare backwardSquare = mock(PlayableSquare.class);
        when(backwardSquare.isEmpty()).thenReturn(true);
        when(backwardSquare.getPlayedPosition()).thenReturn(new PlayedPosition(-1, -1));

        when(mockSquares[2][2]).thenReturn(backwardSquare);
        when(mockSquares[2][2] instanceof PlayableSquare).thenReturn(true);

        List<Move> moves = whitePiece.getPossibleMoves();
        assertTrue(moves.isEmpty(), "Non-king should not be able to move backward");
    }

    @Test
    void testCannotAttackSameColorPiece() {
        // Set up a friendly piece diagonally
        PlayableSquare friendlySquare = mock(PlayableSquare.class);

        Piece friendlyPiece = new Piece(4, friendlySquare, Piece.PieceColor.WHITE);

        when(friendlySquare.isEmpty()).thenReturn(false);
        when(friendlySquare.getPiece()).thenReturn(friendlyPiece);

        // Place the square on the board
        when(mockSquares[3][3]).thenReturn(friendlySquare);
        when(mockSquares[3][3] instanceof PlayableSquare).thenReturn(true);

        List<Move> moves = whitePiece.getPossibleMoves();
        assertTrue(moves.isEmpty(), "Piece should not be able to attack a piece of the same color");
    }
}
