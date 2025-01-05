package be.kdg.integration5.statisticscontext.adapter.out.persistencte.predictions;

import be.kdg.integration5.statisticscontext.domain.Predictions;
import org.springframework.stereotype.Component;

@Component
public class PredictionsJpaConverter {

    public Predictions toDomain(PredictionsJpaEntity entity) {
        return new Predictions(
                entity.getChurn(),
                entity.getFirstMoveWinProbability(),
                entity.getPlayerClass()
        );
    }
}
