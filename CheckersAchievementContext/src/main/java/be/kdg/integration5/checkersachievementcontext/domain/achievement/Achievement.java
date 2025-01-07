package be.kdg.integration5.checkersachievementcontext.domain.achievement;

import be.kdg.integration5.checkersachievementcontext.domain.Game;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import lombok.Getter;

@Getter
public abstract class Achievement {
    private boolean achieved;

    public void open() {
        this.achieved = true;
    }

    public abstract boolean isFulfilled(Game game, PlayerId playerId);
}
