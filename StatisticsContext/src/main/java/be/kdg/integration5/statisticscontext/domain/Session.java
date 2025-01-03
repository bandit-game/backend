package be.kdg.integration5.statisticscontext.domain;


import be.kdg.integration5.statisticscontext.domain.exception.NoMovesInPlayerActivityException;
import be.kdg.integration5.statisticscontext.domain.exception.NotFirstPlayerException;
import be.kdg.integration5.statisticscontext.domain.exception.PlayerNotPartOfSessionException;
import be.kdg.integration5.statisticscontext.domain.exception.SessionResultConflictException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Session {
    private SessionId sessionId;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private boolean isDraw;
    private List<PlayerActivity> activities;
    private Game game;
    private Player winner;

    public Session(SessionId sessionId, Game game) {
        this.sessionId = sessionId;
        this.startTime = null;
        this.finishTime = null;
        this.isDraw = false;
        this.activities = new ArrayList<>();
        this.game = game;
    }

    public void start(LocalDateTime startTime, List<Player> players, PlayerId firstPlayerId) {
        this.startTime = startTime;
        this.activities = players.stream().map(PlayerActivity::new).collect(Collectors.toList());

        PlayerActivity firstPlayerActivity = getPlayerActivity(firstPlayerId);
        firstPlayerActivity.startMove(startTime);
    }

    public void processMove(PlayerId playerId, PlayerId nextPlayerId, LocalDateTime moveDateTime) {
        PlayerActivity playerActivity = this.getPlayerActivity(playerId);

        if (playerActivity.getMoves() == null || playerActivity.getMoves().isEmpty())
            throw new NotFirstPlayerException("Player " + playerId + " is not first player.");

        playerActivity.endMove(moveDateTime);

        PlayerActivity nextPlayerActivity = this.getPlayerActivity(nextPlayerId);
        nextPlayerActivity.startMove(moveDateTime);
    }
    public void gameFinish(PlayerId winnerId, LocalDateTime finishTime, boolean isDraw) {
        if (winnerId != null && activities.stream().noneMatch(activity -> activity.getPlayer().getPlayerId().equals(winnerId)))
            throw new PlayerNotPartOfSessionException("Player " + winnerId + " is not part of session.");

        if (winnerId != null && isDraw)
            throw new SessionResultConflictException("Session cannot have a winner and have draw at the same time.");

        Player firstPlayer = this.getFirstPlayer();

        this.finishTime = finishTime;
        this.isDraw = isDraw;

        if (!isDraw) {
            PlayerActivity winnerActivity = this.getPlayerActivity(winnerId);
            this.winner = winnerActivity.getPlayer();
        }
        activities.forEach(activity -> {
            activity.endMove(finishTime);

            boolean isWinner = false;
            boolean isFirst = firstPlayer.equals(activity.getPlayer());

            if (!isDraw)
                isWinner = activity.getPlayer().getPlayerId().equals(winnerId);

            activity.updateMetrics(isWinner, isDraw, isFirst, this.startTime);
        });

    }

    private PlayerActivity getPlayerActivity(PlayerId playerId) {
        return activities
                .stream()
                .filter(activity -> activity.getPlayer().getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new PlayerNotPartOfSessionException(
                        "Player %s not part of session %s".formatted(playerId.uuid(), this.getSessionId().uuid())));

    }

    private Player getFirstPlayer() {
        return activities.stream()
                .filter(activity -> !activity.getMoves().isEmpty())
                .min(Comparator.comparing(activity -> activity.getFirstMove().getStartTime()))
                .map(PlayerActivity::getPlayer)
                .orElseThrow(() -> new NoMovesInPlayerActivityException("Session " + this.getSessionId().uuid() + " has no moves."));
    }
}
