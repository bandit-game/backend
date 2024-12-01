package be.kdg.integration5.checkerscontext.domain;

import static be.kdg.integration5.checkerscontext.domain.Board.BOARD_SIZE;

public record PlayedPosition(int playedSquareNumber) {
    public PlayedPosition {
        if (playedSquareNumber < 0 || playedSquareNumber > BOARD_SIZE * BOARD_SIZE / 2)
            throw new IllegalArgumentException("Invalid played square number");
    }
}
