package be.kdg.integration5.checkerscontext.adapter.out.persistence.exception;

import jakarta.persistence.EntityNotFoundException;

public class SquareNotFoundException extends EntityNotFoundException {
    public SquareNotFoundException(String message) {
        super(message);
    }
}
