package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.GameId;

public interface DeleteGamePort {
    void deleteById(GameId gameId);
}
