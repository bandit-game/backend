package be.kdg.integration5.checkerscontext.domain.exception;

public class NotPossibleToPlacePieceException extends RuntimeException {
    public NotPossibleToPlacePieceException(String message) {
        super(message);
    }
}
