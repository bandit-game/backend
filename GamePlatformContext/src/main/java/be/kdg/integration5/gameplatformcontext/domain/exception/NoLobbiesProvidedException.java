package be.kdg.integration5.gameplatformcontext.domain.exception;

public class NoLobbiesProvidedException extends RuntimeException {
    public NoLobbiesProvidedException(String message) {
        super(message);
    }
}
