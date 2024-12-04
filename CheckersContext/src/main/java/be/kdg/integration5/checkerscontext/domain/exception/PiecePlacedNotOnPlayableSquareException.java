package be.kdg.integration5.checkerscontext.domain.exception;

public class PiecePlacedNotOnPlayableSquareException extends RuntimeException {
    public PiecePlacedNotOnPlayableSquareException(String message) {
        super(message);
    }
}
