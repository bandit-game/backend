package be.kdg.integration5.checkerscontext.domain;


import be.kdg.integration5.checkerscontext.domain.exception.NotPlayablePositionException;

public record PiecePosition(int x, int y) {
    public PiecePosition {
        if (x < 0 || y < 0 || x >= Board.BOARD_SIZE || y >= Board.BOARD_SIZE)
            throw new IndexOutOfBoundsException("X and Y should be between 0 and " + (Board.BOARD_SIZE - 1));
        if ((x + y) % 2 == 0)
            throw new NotPlayablePositionException("X: " + x + " Y: " + y + " | Is not playable position.");
    }
}
