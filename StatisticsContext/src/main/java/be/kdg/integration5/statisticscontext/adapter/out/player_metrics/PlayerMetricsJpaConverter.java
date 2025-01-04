package be.kdg.integration5.statisticscontext.adapter.out.player_metrics;

import be.kdg.integration5.statisticscontext.domain.Metrics;
import org.springframework.stereotype.Component;

@Component
public class PlayerMetricsJpaConverter {

    public Metrics toDomain(PlayerMetricsJpaEntity entity) {
        return new Metrics(
                entity.getTotalGamesPlayed(),
                entity.getTotalWins(),
                entity.getTotalLosses(),
                entity.getTotalDraws(),
                entity.getTotalIsFirst(),
                entity.getAvgMoveDuration(),
                entity.getAvgMoveAmount(),
                entity.getAvgGameDuration(),
                entity.getTotalWeekdaysPlayed(),
                entity.getTotalWeekendsPlayed(),
                entity.getTotalMorningPlays(),
                entity.getTotalAfternoonPlays(),
                entity.getTotalEveningPlays(),
                entity.getTotalNightPlays()
        );
    }


    public void updateFields(PlayerMetricsJpaEntity metricsJpaEntity, Metrics updatedMetrics) {
        metricsJpaEntity.setTotalGamesPlayed(updatedMetrics.getTotalGamesPlayed());
        metricsJpaEntity.setTotalWins(updatedMetrics.getTotalWins());
        metricsJpaEntity.setTotalLosses(updatedMetrics.getTotalLosses());
        metricsJpaEntity.setTotalDraws(updatedMetrics.getTotalDraws());
        metricsJpaEntity.setTotalIsFirst(updatedMetrics.getTotalIsFirst());
        metricsJpaEntity.setAvgMoveDuration(updatedMetrics.getAvgMoveDuration());
        metricsJpaEntity.setAvgMoveAmount(updatedMetrics.getAvgMoveAmount());
        metricsJpaEntity.setAvgGameDuration(updatedMetrics.getAvgGameDuration());
        metricsJpaEntity.setTotalWeekdaysPlayed(updatedMetrics.getTotalWeekdaysPlayed());
        metricsJpaEntity.setTotalWeekendsPlayed(updatedMetrics.getTotalWeekendsPlayed());
        metricsJpaEntity.setTotalMorningPlays(updatedMetrics.getTotalMorningPlays());
        metricsJpaEntity.setTotalAfternoonPlays(updatedMetrics.getTotalAfternoonPlays());
        metricsJpaEntity.setTotalEveningPlays(updatedMetrics.getTotalEveningPlays());
        metricsJpaEntity.setTotalNightPlays(updatedMetrics.getTotalNightPlays());

    }
}
