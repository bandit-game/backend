package be.kdg.integration5.gameplatformcontext.domain.exception;

public class NotAllowedInLobbyException extends RuntimeException {
    public NotAllowedInLobbyException(String message) {
        super(message);
    }
}
