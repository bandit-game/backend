package be.kdg.integration5.checkerscontext.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Move {
    private MovePosition initialPosition;
    private List<MovePosition> intermediateAttackPositions;
    private MovePosition futurePosition;
    private MoveType type;

    public Move(MovePosition initialPosition, MovePosition futurePosition, MoveType type) {
        this.initialPosition = initialPosition;
        this.futurePosition = futurePosition;
        this.type = type;
    }

    public void addIntermediateAttackPosition(MovePosition position) {
        if (intermediateAttackPositions == null)
            intermediateAttackPositions = new ArrayList<>();

        intermediateAttackPositions.add(position);
    }

    public enum MoveType {
        ATTACK, GO
    }
}
