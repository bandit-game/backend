package be.kdg.integration5.statisticscontext.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Session {
    private SessionId sessionId;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private boolean isDraw;
    private List<PlayerActivity> activities;

    private Player winner;

    public void makeMove(Move move, Player player) {

    }
    public void gameFinish() {
        // update all metrics of all players
    }
}
