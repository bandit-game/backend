package be.kdg.integration5.gameplatformcontext.port.in;

import java.util.Objects;

public record FindPlayersByUsernameCommand(String username) {
    public FindPlayersByUsernameCommand {
        Objects.requireNonNull(username);
    }
}
