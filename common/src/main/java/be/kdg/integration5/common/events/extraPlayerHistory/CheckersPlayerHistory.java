package be.kdg.integration5.common.events.extraPlayerHistory;

import be.kdg.integration5.common.domain.HistorySubType;
import be.kdg.integration5.common.domain.PlayerMatchHistory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@HistorySubType("CHECKERS")
public class CheckersPlayerHistory extends PlayerMatchHistory {
    private int moveAmount;
    private int kingAmount;
    private int largestJump;


    public CheckersPlayerHistory(int moveAmount, int kingAmount, int largestJump) {
        super("CHECKERS");
        this.moveAmount = moveAmount;
        this.kingAmount = kingAmount;
        this.largestJump = largestJump;
    }
    public CheckersPlayerHistory(
            @JsonProperty("type") String type,
            @JsonProperty("moveAmount") int moveAmount,
            @JsonProperty("kingAmount") int kingAmount,
            @JsonProperty("largestJump") int largestJump) {
        super(type);
        this.moveAmount = moveAmount;
        this.kingAmount = kingAmount;
        this.largestJump = largestJump;
    }

}
