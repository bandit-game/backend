package be.kdg.integration5.gameplatformcontext.domain.exception;

public class LobbyGameAlreadyStartedException extends RuntimeException {
    public LobbyGameAlreadyStartedException(String message) {
        super(message);
    }
}
