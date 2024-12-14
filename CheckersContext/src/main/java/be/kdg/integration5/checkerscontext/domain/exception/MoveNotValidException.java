package be.kdg.integration5.checkerscontext.domain.exception;

import be.kdg.integration5.checkerscontext.domain.PlayerId;
import lombok.Getter;

@Getter
public class MoveNotValidException extends RuntimeException {
    private final PlayerId playerId;
    public MoveNotValidException(String message, PlayerId playerId) {
        super(message);
        this.playerId = playerId;
    }
}
