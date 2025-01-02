package be.kdg.integration5.statisticscontext.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    public void start(LocalDateTime startTime, List<Player> players) {
        this.startTime = startTime;
        this.activities = players.stream().map(PlayerActivity::new).collect(Collectors.toList());
    }

    public void makeMove(Move move, Player player) {

    }
    public void gameFinish() {
        // update all metrics of all players
    }
}
