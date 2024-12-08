package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.PlayerId;

public interface GetStateOfGameUseCase {
    void sendStateTo(PlayerId playerId);
}
