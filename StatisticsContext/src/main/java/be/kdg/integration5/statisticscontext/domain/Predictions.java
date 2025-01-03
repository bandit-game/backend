package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Predictions {
    private double churn;
    private double firstMoveWinProbability;
    private String skillLevel;
    private double consistencyScore;
    private double platformEngagementProbability;

}


