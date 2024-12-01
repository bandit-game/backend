package be.kdg.integration5.gameplatformcontext.adapter.in.messaging;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.port.out.NotifyGameStartPort;
import org.springframework.stereotype.Component;

@Component
public class GameStartPublisher implements NotifyGameStartPort {
    @Override
    public void notifyGameStart(Lobby lobby) {

    }
}
