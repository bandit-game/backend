package be.kdg.integration5.checkerscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
//@ToString
public class Board {
    public static final int BOARD_SIZE = 10;
    public static final int PIECES_PER_PLAYER = 20;

    private Game game;

    private Square[][] squares;
    private Player currentPlayer;

    public Board(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
    }

    {
        this.squares = new Square[BOARD_SIZE][BOARD_SIZE];
        generateBoard();
        placePieces();
    }

    private void placePieces() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 1) {
                    if (i*10 + j < PIECES_PER_PLAYER*2)
                        new Piece(j, squares[i / 10][i % 10 + 1], Piece.PieceColor.BLACK);
                    else if (i*10 + j > BOARD_SIZE * BOARD_SIZE - PIECES_PER_PLAYER*2)
                        new Piece(j, squares[(BOARD_SIZE - 1) - i / 10][(BOARD_SIZE - 1) - i % 10 - 1], Piece.PieceColor.WHITE);
                }
            }
        }
    }

    private void generateBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 1)
                    squares[i][j] = new Square(this, i * 10 + j, new PlayedPosition(j, i));
                else
                    squares[i][j] = new Square(this, i * 10 + j);
            }
        }
    }
}
