package be.kdg.integration5.guessitcontext.exception;

import jakarta.persistence.EntityNotFoundException;

public class SessionNotFoundException extends EntityNotFoundException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
