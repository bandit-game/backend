package be.kdg.integration5.statisticscontext.adapter.out.player;

import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import be.kdg.integration5.statisticscontext.port.out.FindPlayerPort;
import be.kdg.integration5.statisticscontext.port.out.PersistPlayerPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PlayerDatabaseAdapter implements FindPlayerPort, PersistPlayerPort {

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerJpaConverter playerJpaConverter;

    public PlayerDatabaseAdapter(PlayerJpaRepository playerJpaRepository, PlayerJpaConverter playerJpaConverter) {
        this.playerJpaRepository = playerJpaRepository;
        this.playerJpaConverter = playerJpaConverter;
    }

    @Override
    public List<Player> findPlayersByIds(List<PlayerId> playerIds) {
        List<UUID> playerUuids = playerIds.stream().map(PlayerId::uuid).toList();
        List<PlayerJpaEntity> playerJpaEntities = playerJpaRepository.findAllById(playerUuids);

        return playerJpaEntities.stream().map(playerJpaConverter::toDomain).collect(Collectors.toList());
    }
}
