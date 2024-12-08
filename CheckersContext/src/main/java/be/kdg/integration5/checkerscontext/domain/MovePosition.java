package be.kdg.integration5.checkerscontext.domain;


public record MovePosition(int x, int y) {
    public MovePosition {
        if (x < 0 || y < 0 || x >= Board.BOARD_SIZE || y >= Board.BOARD_SIZE)
            throw new IndexOutOfBoundsException();
    }
}
