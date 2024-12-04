package be.kdg.integration5.checkerscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Move {
    private PlayedPosition initialPosition;
    private List<PlayedPosition> intermediateAttackPositions;
    private PlayedPosition futurePosition;
    private MoveType type;

    public void addIntermediateAttackPosition(PlayedPosition position) {
        if (intermediateAttackPositions == null)
            intermediateAttackPositions = new ArrayList<>();

        intermediateAttackPositions.add(position);
    }

    public enum MoveType {
        ATTACK, GO
    }
}
