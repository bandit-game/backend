package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.exception.PlayerNotFoundException;
import be.kdg.integration5.checkersachievementcontext.domain.Player;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.port.out.FindPlayerPort;
import be.kdg.integration5.checkersachievementcontext.port.out.PersistPlayerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerDatabaseAdapter implements FindPlayerPort, PersistPlayerPort {
    private final PlayerJpaRepository playerJpaRepository;

    @Autowired
    public PlayerDatabaseAdapter(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public Player findById(PlayerId playerId) {
        return playerJpaRepository.findById(playerId.uuid()).orElseThrow(
                () -> new PlayerNotFoundException("Player with given id [%s] is not found".formatted(playerId.uuid()))
        ).toDomain();
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        List<PlayerJpaEntity> savedPlayerJpaEntities = playerJpaRepository.saveAll(players.stream().map(PlayerJpaEntity::of).toList());
        return savedPlayerJpaEntities.stream().map(PlayerJpaEntity::toDomain).toList();
    }
}
