package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Metrics {
    private int totalGamesPlayed;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;
    private int totalIsFirst;
    private double avgMoveDuration;
    private double avgMoveAmount;
    private double avgGameDuration;
    private int totalWeekdaysPlayed;
    private int totalWeekendsPlayed;
    private int totalMorningPlays;
    private int totalAfternoonPlays;
    private int totalEveningPlays;
    private int totalNightPlays;

}
