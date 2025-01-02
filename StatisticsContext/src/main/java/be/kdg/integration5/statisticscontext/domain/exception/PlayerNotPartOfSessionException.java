package be.kdg.integration5.statisticscontext.domain.exception;

public class PlayerNotPartOfSessionException extends IllegalStateException {
    public PlayerNotPartOfSessionException(String message) {
        super(message);
    }
}
