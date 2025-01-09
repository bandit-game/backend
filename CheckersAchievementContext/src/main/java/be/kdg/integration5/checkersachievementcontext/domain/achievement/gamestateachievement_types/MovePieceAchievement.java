package be.kdg.integration5.checkersachievementcontext.domain.achievement.gamestateachievement_types;

import be.kdg.integration5.checkersachievementcontext.domain.Game;
import be.kdg.integration5.checkersachievementcontext.domain.Move;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.GameStateAchievement;

import java.util.List;

public class MovePieceAchievement extends GameStateAchievement {

    public MovePieceAchievement(String name, String description, String imagUrl) {
        super(name, description, imagUrl);
    }

    public MovePieceAchievement(String name, String description, String imagUrl, boolean isAchieved) {
        super(name, description, imagUrl, isAchieved);
    }

    @Override
    public boolean isConditionMet(Game game, PlayerId playerId) {
        List<Move> movesHistory = game.getBoard().movesHistory();
        return movesHistory.stream().anyMatch(move -> move.mover().getPlayerId().equals(playerId));
    }
}
