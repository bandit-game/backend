package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Predictions {
    private double churn;
    private double firstMoveWinProbability;
    private PlayerClass playerClass;
    // private double consistencyScore;

    public static List<String> getPredictionsList() {
        return List.of("CHURN", "FIRST_MOVE_WIN_PROBABILITY", "PLAYER_CLASS");
    }

    public enum PlayerClass {
        BEGINNER, FREQUENT_PLAYER, PRO_GAMER, WORK_TIME_PLAYER, OWL_PLAYER, FAST_THINKER, TURTLE_SPEED
    }
}


