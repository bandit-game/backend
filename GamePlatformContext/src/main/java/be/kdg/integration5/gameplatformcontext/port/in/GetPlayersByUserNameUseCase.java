package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.Player;

import java.util.List;

public interface GetPlayersByUserNameUseCase {
    List<Player> getPlayers(String username);
}
