package be.kdg.integration5.statisticscontext.domain;

import be.kdg.integration5.statisticscontext.domain.exception.MoveDateTimeConflictException;
import be.kdg.integration5.statisticscontext.domain.exception.MoveWithoutEndTimeException;
import be.kdg.integration5.statisticscontext.domain.exception.NoMovesInPlayerActivityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerActivity {
    private Player player;
    private List<Move> moves;

    public PlayerActivity(Player player) {
        this.player = player;
        this.moves = new ArrayList<>();
    }

    public void startMove(LocalDateTime moveDateTime) {
        moves.add(new Move(moveDateTime, moves.size() + 1));
    }

    public void endMove(LocalDateTime moveDateTime) {
        Move move = this.getLastMove();

        if (moveDateTime.isBefore(move.getStartTime()))
            throw new MoveDateTimeConflictException("Move datetime cannot be before start time.");

        if (move.getEndTime() == null)
            move.setEndTime(moveDateTime);
    }

    public void updateMetrics(boolean isWinner, boolean isDraw, boolean isFirstMove, LocalDateTime startTime, LocalDateTime finishTime) {
        Move lastMove = this.getLastMove();

        // Calculate game duration
        double gameDuration = Duration.between(startTime, finishTime).toSeconds();

        // Calculate average move duration
        double avgMoveDuration = moves.stream()
                .filter(move -> move.getEndTime() != null)
                .mapToDouble(move -> Duration.between(move.getStartTime(), move.getEndTime()).toSeconds())
                .average()
                .orElse(0);

        // Calculate total moves
        double totalMoves = moves.size();

        // Update player metrics
        Metrics metrics = player.getMetrics();
        metrics.updateValues(isWinner, isDraw, isFirstMove, gameDuration, avgMoveDuration, totalMoves, startTime);
    }

    public Move getLastMove() {
        return moves.stream().max(Comparator.comparing(Move::getStartTime))
                .orElseThrow(() -> new NoMovesInPlayerActivityException("No moves found for player %s".formatted(player.getPlayerId().uuid())));
    }

    public Move getFirstMove() {
        return moves.stream().min(Comparator.comparing(Move::getStartTime))
                .orElseThrow(() -> new NoMovesInPlayerActivityException("No moves found for player %s".formatted(player.getPlayerId().uuid())));
    }

}
