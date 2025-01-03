package be.kdg.integration5.statisticscontext.adapter.out.player_metrics;


import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "player_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerMetricsJpaEntity {

    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private PlayerJpaEntity player;

    @Column(name = "total_games_played", nullable = false)
    private int totalGamesPlayed;

    @Column(name = "total_wins", nullable = false)
    private int totalWins;

    @Column(name = "total_losses", nullable = false)
    private int totalLosses;

    @Column(name = "total_draws", nullable = false)
    private int totalDraws;

    @Column(name = "total_is_first", nullable = false)
    private int totalIsFirst;

    @Column(name = "avg_move_duration", nullable = false)
    private double avgMoveDuration;

    @Column(name = "avg_move_amount", nullable = false)
    private double avgMoveAmount;

    @Column(name = "avg_game_duration", nullable = false)
    private double avgGameDuration;

    @Column(name = "total_weekdays_played", nullable = false)
    private int totalWeekdaysPlayed;

    @Column(name = "total_weekends_played", nullable = false)
    private int totalWeekendsPlayed;

    @Column(name = "total_morning_plays", nullable = false)
    private int totalMorningPlays;

    @Column(name = "total_afternoon_plays", nullable = false)
    private int totalAfternoonPlays;

    @Column(name = "total_evening_plays", nullable = false)
    private int totalEveningPlays;

    @Column(name = "total_night_plays", nullable = false)
    private int totalNightPlays;

    public PlayerMetricsJpaEntity(UUID playerId, int totalGamesPlayed, int totalWins, int totalLosses, int totalDraws, int totalIsFirst, double avgMoveDuration, double avgMoveAmount, double avgGameDuration, int totalWeekdaysPlayed, int totalWeekendsPlayed, int totalMorningPlays, int totalAfternoonPlays, int totalEveningPlays, int totalNightPlays) {
        this.playerId = playerId;
        this.totalGamesPlayed = totalGamesPlayed;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.totalDraws = totalDraws;
        this.totalIsFirst = totalIsFirst;
        this.avgMoveDuration = avgMoveDuration;
        this.avgMoveAmount = avgMoveAmount;
        this.avgGameDuration = avgGameDuration;
        this.totalWeekdaysPlayed = totalWeekdaysPlayed;
        this.totalWeekendsPlayed = totalWeekendsPlayed;
        this.totalMorningPlays = totalMorningPlays;
        this.totalAfternoonPlays = totalAfternoonPlays;
        this.totalEveningPlays = totalEveningPlays;
        this.totalNightPlays = totalNightPlays;
    }
}
