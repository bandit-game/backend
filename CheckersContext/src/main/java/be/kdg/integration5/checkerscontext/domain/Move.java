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
    private PiecePosition initialPosition;
    private List<PiecePosition> intermediateAttackPositions;
    private PiecePosition futurePosition;
    private MoveType type;

    public Move(PiecePosition initialPosition, PiecePosition futurePosition, MoveType type) {
        this.initialPosition = initialPosition;
        this.futurePosition = futurePosition;
        this.type = type;
    }

    public void addIntermediateAttackPosition(PiecePosition position) {
        if (intermediateAttackPositions == null)
            intermediateAttackPositions = new ArrayList<>();

        intermediateAttackPositions.add(position);
    }

    public List<PiecePosition> getAllAttackSteps() {
        this.intermediateAttackPositions.add(futurePosition);
        return intermediateAttackPositions;
    }

    public enum MoveType {
        ATTACK, GO
    }
}
