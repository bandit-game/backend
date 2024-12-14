package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.in.GetGameStateUseCase;
import be.kdg.integration5.checkerscontext.port.in.SendGameStateToPlayerCommand;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.NotifyPlayerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetGameStateUseCaseImpl implements GetGameStateUseCase {

    private final NotifyPlayerPort notifyPlayerPort;
    private final FindGamePort findGamePort;

    public GetGameStateUseCaseImpl(NotifyPlayerPort notifyPlayerPort, FindGamePort findGamePort) {
        this.notifyPlayerPort = notifyPlayerPort;
        this.findGamePort = findGamePort;
    }

    @Override
    public void sendGameStateToPlayer(SendGameStateToPlayerCommand sendGameStateToPlayerCommand) {
        GameId gameId = sendGameStateToPlayerCommand.gameId();
        PlayerId playerId = sendGameStateToPlayerCommand.playerId();

        Game game = findGamePort.findById(gameId);
        if (game.playerIsParticipant(playerId))
            notifyPlayerPort.notifyPlayerOfGameState(playerId, game);
    }
}
