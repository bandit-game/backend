package be.kdg.integration5.gameplatformcontext.domain.exception;

public class LobbyIsFullException extends RuntimeException {
    public LobbyIsFullException(String message) {
        super(message);
    }
}
