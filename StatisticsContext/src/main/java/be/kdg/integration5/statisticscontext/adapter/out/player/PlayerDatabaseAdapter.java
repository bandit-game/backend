package be.kdg.integration5.statisticscontext.adapter.out.player;

import be.kdg.integration5.statisticscontext.adapter.out.prediction.db.PredictionsJpaEntity;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import be.kdg.integration5.statisticscontext.domain.Predictions;
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
    public void updateAll(List<Player> players) {
        List<UUID> playerUuids = players.stream().map(p -> p.getPlayerId().uuid()).toList();
        List<PlayerJpaEntity> playerJpaEntities = playerJpaRepository.findAllByIdsFetched(playerUuids);
        Map<UUID, Player> playerMap = players.stream()
                .collect(Collectors.toMap(p -> p.getPlayerId().uuid(), p -> p));

        playerJpaEntities.forEach(playerJpaEntity -> {
            Player domainPlayer = playerMap.get(playerJpaEntity.getPlayerId());
            Predictions domainPredictions = domainPlayer.getPredictions();
            PredictionsJpaEntity predictionsJpaEntity = playerJpaEntity.getPredictions();

            predictionsJpaEntity.setChurn(domainPredictions.getChurn());
            predictionsJpaEntity.setFirstMoveWinProbability(domainPredictions.getFirstMoveWinProbability());
            predictionsJpaEntity.setPlayerClass(domainPredictions.getPlayerClass());
        });

        playerJpaRepository.saveAll(playerJpaEntities);
    }
}
