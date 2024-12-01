package be.kdg.integration5.gameplatformcontext.domain.exception;

public class LobbyIsNotFullException extends RuntimeException{
    public LobbyIsNotFullException(String message) {
        super(message);
    }
}
