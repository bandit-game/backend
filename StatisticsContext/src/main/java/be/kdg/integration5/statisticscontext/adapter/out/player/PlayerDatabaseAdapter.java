package be.kdg.integration5.statisticscontext.adapter.out.player;

import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import be.kdg.integration5.statisticscontext.domain.SessionId;
import be.kdg.integration5.statisticscontext.port.out.FindPlayerPort;
import be.kdg.integration5.statisticscontext.port.out.PersistPlayerPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        List<PlayerJpaEntity> playerJpaEntities = playerJpaRepository.findAllByIdsFetched(playerUuids);

        return playerJpaEntities.stream().map(playerJpaConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void updateAllInSession(List<Player> players, SessionId sessionId) {
        List<PlayerJpaEntity> existingEntities = playerJpaRepository.findAllById(players.stream().map(p -> p.getPlayerId().uuid()).toList());
        Map<UUID, Player> playerMap = players.stream()
                .collect(Collectors.toMap(p -> p.getPlayerId().uuid(), p -> p));

        existingEntities = existingEntities
                .stream()
                .map(entity -> {
                    Player player = playerMap.get(entity.getPlayerId());
                    if (player != null) {
                        return playerJpaConverter.toJpa(player); // Update the entity
                    }
                    return null;
                })
                .collect(Collectors.toList());


        playerJpaRepository.saveAll(players.stream().map(playerJpaConverter::toJpa).toList());
    }
}
