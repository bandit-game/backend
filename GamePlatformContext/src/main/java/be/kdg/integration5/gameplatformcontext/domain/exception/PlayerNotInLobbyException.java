package be.kdg.integration5.gameplatformcontext.domain.exception;

public class PlayerNotInLobbyException extends RuntimeException {
    public PlayerNotInLobbyException(String msg) {
        super(msg);
    }
}
