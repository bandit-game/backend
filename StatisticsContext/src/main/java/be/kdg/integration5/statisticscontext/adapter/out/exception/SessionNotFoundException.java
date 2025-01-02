package be.kdg.integration5.statisticscontext.adapter.out.exception;

import jakarta.persistence.EntityNotFoundException;

public class SessionNotFoundException extends EntityNotFoundException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
