package be.kdg.integration5.checkerscontext.domain;

import static be.kdg.integration5.checkerscontext.domain.Board.BOARD_SIZE;

public record Position(int squareNumber) {
    public Position {
        if (squareNumber < 0 || squareNumber > BOARD_SIZE * BOARD_SIZE / 2)
            throw new IllegalArgumentException("Invalid square number");
    }
}
