package be.kdg.integration5.checkersachievementcontext.domain.achievement.cumulativeachievement_types;

import be.kdg.integration5.checkersachievementcontext.domain.Game;
import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.CumulativeAchievement;

import java.util.HashSet;
import java.util.Set;

public class PlayNGamesAchievement extends CumulativeAchievement<Integer, Set<GameId>> {
    public PlayNGamesAchievement(String name, String description, String imageUrl, Integer desiredAmount) {
        super(name, description, imageUrl, desiredAmount, new HashSet<>());
    }

    public PlayNGamesAchievement(String name, String description, String imagUrl, boolean isAchieved, Integer goal, Set<GameId> counter) {
        super(name, description, imagUrl, isAchieved, goal, counter);
    }

    @Override
    public boolean compare(Integer goal, Set<GameId> counter) {
        return counter.size() >= goal;
    }

    @Override
    public void checkConditionAndHandle(Game game, PlayerId playerId) {
        if (game != null && game.getPlayers().stream().anyMatch(player -> player.getPlayerId().equals(playerId)))
            counter.add(game.getGameId());
    }
}
