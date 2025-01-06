package be.kdg.integration5.statisticscontext.adapter.out.persistencte.player;

import be.kdg.integration5.statisticscontext.adapter.out.persistencte.player_metrics.PlayerMetricsJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.predictions.PredictionsJpaConverter;
import be.kdg.integration5.statisticscontext.domain.Location;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerJpaConverter {
    private final PlayerMetricsJpaConverter playerMetricsJpaConverter;
    private final PredictionsJpaConverter predictionsJpaConverter;

    public PlayerJpaConverter(PlayerMetricsJpaConverter playerMetricsJpaConverter, PredictionsJpaConverter predictionsJpaConverter) {
        this.playerMetricsJpaConverter = playerMetricsJpaConverter;
        this.predictionsJpaConverter = predictionsJpaConverter;
    }

    public PlayerJpaEntity toJpa(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().uuid(),
                player.getLocation().city(),
                player.getLocation().country(),
                player.getGender().name(),
                player.getAge(),
                player.getPlayerName()
        );
    }

    public Player toDomain(PlayerJpaEntity entity) {
        return new Player(
                new PlayerId(entity.getPlayerId()),
                entity.getPlayerName(),
                entity.getAge(),
                Player.Gender.valueOf(entity.getGender()),
                new Location(entity.getCountry(), entity.getCity()),
                entity.getPlayerMetrics() == null ? null : playerMetricsJpaConverter.toDomain(entity.getPlayerMetrics()),
                entity.getPredictions() == null ? null : predictionsJpaConverter.toDomain(entity.getPredictions()));
    }

}
