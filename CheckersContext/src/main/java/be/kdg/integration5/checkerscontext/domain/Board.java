package be.kdg.integration5.checkerscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class Board {
    public static final int BOARD_SIZE = 10;
    public static final int PIECES_PER_PLAYER = 20;

    private Game game;

    private Square[][] squares;
    private Player currentPlayer;

    public Board(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
        this.squares = new Square[BOARD_SIZE][BOARD_SIZE];
        generateBoard();
        placePieces();
    }

    private void placePieces() {
        int pieceCounter = 0;
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if ((y + x) % 2 == 1) {
                    if (y * 10 + x < PIECES_PER_PLAYER * 2)
                        //TODO Might need to assign it later (it works)
                        new Piece(++pieceCounter, squares[y][x], Piece.PieceColor.BLACK);
                    else if (y * 10 + x > BOARD_SIZE * BOARD_SIZE - PIECES_PER_PLAYER * 2)
                        new Piece(++pieceCounter, squares[y][x], Piece.PieceColor.WHITE)
                    ;
                }
            }
        }
    }

    private void generateBoard() {
        for (int y = 0; y < BOARD_SIZE; y++)
            for (int x = 0; x < BOARD_SIZE; x++)
                squares[y][x] = new Square(this, x, y);
    }
}
