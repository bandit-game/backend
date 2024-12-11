package be.kdg.integration5.checkerscontext.domain.exception;

public class MoveNotValidException extends RuntimeException {
    public MoveNotValidException(String message) {
        super(message);
    }
}
