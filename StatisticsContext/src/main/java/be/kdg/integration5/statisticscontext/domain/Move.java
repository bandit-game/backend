package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Move {
    private MoveId moveId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int moveNumber;
}
