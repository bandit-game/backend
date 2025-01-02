package be.kdg.integration5.statisticscontext.domain;

import be.kdg.integration5.statisticscontext.domain.exception.MoveDateTimeConflictException;
import be.kdg.integration5.statisticscontext.domain.exception.NoMovesInPlayerActivityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

        move.setEndTime(moveDateTime);
    }

    public Move getLastMove() {
        return moves.stream().max(Comparator.comparing(Move::getStartTime))
                .orElseThrow(() -> new NoMovesInPlayerActivityException("No moves found for player %s".formatted(player.getPlayerId().uuid())));
    }

}
