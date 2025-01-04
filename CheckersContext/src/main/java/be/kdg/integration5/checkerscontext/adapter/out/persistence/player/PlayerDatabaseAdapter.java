package be.kdg.integration5.checkerscontext.adapter.out.persistence.player;

import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.port.out.PersistPlayerPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerDatabaseAdapter implements PersistPlayerPort {

    private final PlayerJpaRepository playerJpaRepository;

    public PlayerDatabaseAdapter(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public Player save(Player player) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.save(PlayerJpaEntity.of(player));
        return playerJpaEntity.toDomain();
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        List<PlayerJpaEntity> playerJpaEntities = playerJpaRepository.saveAll(players.stream().map(PlayerJpaEntity::of).collect(Collectors.toList()));
        return playerJpaEntities.stream().map(PlayerJpaEntity::toDomain).collect(Collectors.toList());
    }
}
