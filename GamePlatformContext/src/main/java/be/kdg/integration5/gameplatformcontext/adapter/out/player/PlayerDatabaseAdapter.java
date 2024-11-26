package be.kdg.integration5.gameplatformcontext.adapter.out.player;

import be.kdg.integration5.gameplatformcontext.adapter.out.exception.PlayerNotFoundException;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.out.FindPlayerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDatabaseAdapter implements FindPlayerPort {
    private final PlayerJpaRepository playerJpaRepository;

    @Autowired
    public PlayerDatabaseAdapter(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public Player findPlayerById(PlayerId playerId) {
        return playerJpaRepository.findById(playerId.uuid()).orElseThrow(
                () -> new PlayerNotFoundException("Player with the given Id[%s] was not found.".formatted(playerId.uuid()))
        ).toDomain();
    }
}
