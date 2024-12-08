package be.kdg.integration5.gameplatformcontext.adapter.out.exception;

import jakarta.persistence.EntityNotFoundException;

public class GameNotFoundException extends EntityNotFoundException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
