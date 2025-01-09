package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player;

import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.exception.PlayerNotFoundException;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.out.FindPlayerPort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistPlayerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlayerDatabaseAdapter implements FindPlayerPort, PersistPlayerPort {
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

    @Override
    public boolean playerExists(PlayerId playerId) {
        Optional<PlayerJpaEntity> player = playerJpaRepository.findById(playerId.uuid());
        return player.isPresent();

    }

    @Override
    public List<Player> findByUsername(String username) {
        return playerJpaRepository.findAllByUsernameContainingIgnoreCase(username).stream()
                .map(PlayerJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Player save(Player player) {
        return playerJpaRepository.save(PlayerJpaEntity.of(player)).toDomain();
    }
}
