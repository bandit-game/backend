package be.kdg.integration5.checkerscontext.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private static final int BOARD_SIZE = 10;
    private static final int PIECES_PER_PLAYER = 10;

    private Square[][] squares;
    private Player currentPlayer;

    public Board(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.squares = new Square[BOARD_SIZE][BOARD_SIZE];
    }

    {
        generateBoard();
        placePieces();
    }

    private void placePieces() {
        for (int i = 0; i < PIECES_PER_PLAYER; i++) {
            if(squares[i%10][i] instanceof PlayableSquare playableSquare)
                playableSquare.setPlacedPiece(new Piece(Piece.PieceColor.BLACK));
            if(squares[(PIECES_PER_PLAYER - 1) - i%10][(PIECES_PER_PLAYER - 1) - i] instanceof PlayableSquare playableSquare)
                playableSquare.setPlacedPiece(new Piece(Piece.PieceColor.WHITE));
        }
    }

    private void generateBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0))
                    squares[i][j] = new PlayableSquare();
                else
                    squares[i][j] = new VoidSquare();
            }
        }
    }
}
