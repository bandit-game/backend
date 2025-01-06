package be.kdg.integration5.checkerscontext.port.out;


import be.kdg.integration5.checkerscontext.domain.Game;

public interface NotifyGameEndPort {
    void notifyGameEnd(Game game);
}
