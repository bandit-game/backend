package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.Game;

public interface PersistGamePort {
    Game save(Game game);
}
