package be.kdg.integration5.checkersachievementcontext.domain.achievement;

import be.kdg.integration5.checkersachievementcontext.domain.Game;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CumulativeAchievement<T extends Comparable<T>> extends Achievement {
    protected final T desiredAmount;
    protected T counter;

    public abstract void increment();

    public abstract boolean conditionToIncrementIsMet(Game game, PlayerId playerId);

    @Override
    public boolean isFulfilled(Game game, PlayerId playerId) {
        if (conditionToIncrementIsMet(game, playerId))
            increment();

        return counter.compareTo(desiredAmount) >= 0;
    }
}
