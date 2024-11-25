package be.kdg.integration5.gameplatformcontext.adapter.out.exception;

import jakarta.persistence.EntityNotFoundException;

public class PlayerNotFoundException extends EntityNotFoundException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
