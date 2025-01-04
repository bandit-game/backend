package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public void updateValues(
            boolean isWinner,
            boolean isDraw,
            boolean isFirstMove,
            double gameDuration,
            double avgMoveDuration,
            double totalMoves,
            LocalDateTime startTime
    ) {
        // Increment total games played
        this.totalGamesPlayed++;

        // Update win/loss/draw metrics
        if (isWinner) {
            this.totalWins++;
        } else if (isDraw) {
            this.totalDraws++;
        } else {
            this.totalLosses++;
        }

        // Increment total first moves
        if (isFirstMove) {
            this.totalIsFirst++;
        }

        // Update average game duration
        this.avgGameDuration = ((this.avgGameDuration * (this.totalGamesPlayed - 1)) + gameDuration) / this.totalGamesPlayed;

        // Update average move duration
        this.avgMoveDuration = ((this.avgMoveDuration * (this.totalGamesPlayed - 1)) + avgMoveDuration) / this.totalGamesPlayed;

        // Update average move amount
        this.avgMoveAmount = ((this.avgMoveAmount * (this.totalGamesPlayed - 1)) + totalMoves) / this.totalGamesPlayed;

        // Update time-based metrics
        DayOfWeek dayOfWeek = startTime.getDayOfWeek();
        if (dayOfWeek.getValue() <= 5) {
            this.totalWeekdaysPlayed++;
        } else {
            this.totalWeekendsPlayed++;
        }

        // Determine part of the day
        int hour = startTime.getHour();
        if (hour >= 6 && hour < 12) {
            this.totalMorningPlays++;
        } else if (hour >= 12 && hour < 18) {
            this.totalAfternoonPlays++;
        } else if (hour >= 18 && hour < 24) {
            this.totalEveningPlays++;
        } else {
            this.totalNightPlays++;
        }
    }

}
