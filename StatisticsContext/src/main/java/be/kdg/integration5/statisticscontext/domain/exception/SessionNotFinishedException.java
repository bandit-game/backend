package be.kdg.integration5.statisticscontext.domain.exception;

public class SessionNotFinishedException extends IllegalStateException {
    public SessionNotFinishedException(String s) {
        super(s);
    }
}
