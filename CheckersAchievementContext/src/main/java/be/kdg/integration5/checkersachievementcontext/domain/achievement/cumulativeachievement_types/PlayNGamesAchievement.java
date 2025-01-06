package be.kdg.integration5.checkersachievementcontext.domain.achievement.cumulativeachievement_types;

import be.kdg.integration5.checkersachievementcontext.domain.Game;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.CumulativeAchievement;

public class PlayNGamesAchievement extends CumulativeAchievement<Integer> {
    public PlayNGamesAchievement(Integer numGames) {
        super(numGames, 0);
    }

    @Override
    public void increment() {
        this.counter++;
    }

    @Override
    public boolean conditionToIncrementIsMet(Game game, PlayerId playerId) {
        return game != null && game.getPlayers().stream().anyMatch(player -> player.getPlayerId().equals(playerId));
    }
}
