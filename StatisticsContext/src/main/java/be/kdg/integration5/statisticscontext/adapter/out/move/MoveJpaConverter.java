package be.kdg.integration5.statisticscontext.adapter.out.move;

import be.kdg.integration5.statisticscontext.domain.Move;
import be.kdg.integration5.statisticscontext.domain.MoveId;
import org.springframework.stereotype.Component;

@Component
public class MoveJpaConverter {

    public MoveJpaEntity toJpa(Move move) {
        return new MoveJpaEntity(
                move.getMoveId().uuid(),
                move.getMoveNumber(),
                move.getEndTime(),
                move.getStartTime()
        );
    }

    public Move toDomain(MoveJpaEntity moveJpaEntity) {
        return new Move(
                new MoveId(moveJpaEntity.getMoveId()),
                moveJpaEntity.getStartTime(),
                moveJpaEntity.getEndTime(),
                moveJpaEntity.getMoveNumber()
        );
    }
}
