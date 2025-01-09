package be.kdg.integration5.statisticscontext.adapter.out.persistencte.exception;

import jakarta.persistence.EntityNotFoundException;

public class GameNotFoundException extends EntityNotFoundException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
