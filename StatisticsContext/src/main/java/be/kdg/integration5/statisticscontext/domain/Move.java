package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Move {
    private MoveId moveId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int moveNumber;

    public Move(LocalDateTime startTime, int moveNumber) {
        this.startTime = startTime;
        this.moveNumber = moveNumber;
        this.moveId = new MoveId(UUID.randomUUID());
        this.endTime = null;
    }
}
