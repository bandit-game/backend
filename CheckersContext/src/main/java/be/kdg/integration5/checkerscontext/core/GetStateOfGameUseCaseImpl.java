package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.in.GetStateOfGameUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.NotifyPlayerPort;
import org.springframework.stereotype.Service;

@Service
public class GetStateOfGameUseCaseImpl implements GetStateOfGameUseCase {

    private final NotifyPlayerPort notifyPlayerPort;
    private final FindGamePort findGamePort;

    public GetStateOfGameUseCaseImpl(NotifyPlayerPort notifyPlayerPort, FindGamePort findGamePort) {
        this.notifyPlayerPort = notifyPlayerPort;
        this.findGamePort = findGamePort;
    }

    @Override
    public void sendStateTo(PlayerId playerId) {
        Game game = findGamePort.findGameByPlayerAndGameEndNull(playerId);
        notifyPlayerPort.notifyPlayerOfHisGame(playerId, game);
    }
}
