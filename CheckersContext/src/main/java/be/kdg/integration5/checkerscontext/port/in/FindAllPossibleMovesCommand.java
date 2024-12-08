package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.GameId;
import com.rabbitmq.client.Command;

import java.util.Objects;

public record FindAllPossibleMovesCommand(GameId gameId, Integer x, Integer y) {
    public FindAllPossibleMovesCommand {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
    }
}
