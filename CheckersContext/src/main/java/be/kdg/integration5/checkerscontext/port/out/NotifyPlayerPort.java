package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Player;

import java.util.List;

public interface NotifyPlayerPort {

    void notifyPossibleMovesForPlayer(List<Move> moves, Player player);
}
