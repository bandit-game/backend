package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Move {
    private PiecePosition initialPosition;
    private List<PiecePosition> intermediateAttackPositions;
    private PiecePosition futurePosition;
    private MoveType type;

    public Move(PiecePosition initialPosition, PiecePosition futurePosition, MoveType type) {
        this.initialPosition = initialPosition;
        this.futurePosition = futurePosition;
        this.type = type;
        this.intermediateAttackPositions = new ArrayList<>();
    }

    public int getMoveLength() {
        return intermediateAttackPositions.size() + 2;
    }

    public void addIntermediateAttackPosition(PiecePosition position) {
        addIntermediateAttackPosition(position, false);
    }

    public void addIntermediateAttackPosition(PiecePosition position, boolean inBeginning) {
        if (intermediateAttackPositions == null)
            intermediateAttackPositions = new ArrayList<>();

        if (inBeginning)
            intermediateAttackPositions.addFirst(position);
        else
            intermediateAttackPositions.add(position);
    }

    public void addNewInitialPositionInSequence(PiecePosition newInitialPosition) {
        addIntermediateAttackPosition(this.initialPosition, true);

        this.initialPosition = newInitialPosition;
    }

    public void addNewFuturePositionInSequence(PiecePosition newFuturePosition) {
        addIntermediateAttackPosition(this.futurePosition);

        this.futurePosition = newFuturePosition;
    }

    public List<PiecePosition> getAllAttackSteps() {
        List<PiecePosition> allAttackPositions = new ArrayList<>();

        if (intermediateAttackPositions != null)
            allAttackPositions.addAll(intermediateAttackPositions);

        allAttackPositions.add(futurePosition);

        return Collections.unmodifiableList(allAttackPositions);
    }

    public enum MoveType {
        ATTACK, GO
    }
}
