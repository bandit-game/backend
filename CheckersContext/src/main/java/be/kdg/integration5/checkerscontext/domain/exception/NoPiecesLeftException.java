package be.kdg.integration5.checkerscontext.domain.exception;

public class NoPiecesLeftException extends RuntimeException {
    public NoPiecesLeftException(String message) {
        super(message);
    }
}
