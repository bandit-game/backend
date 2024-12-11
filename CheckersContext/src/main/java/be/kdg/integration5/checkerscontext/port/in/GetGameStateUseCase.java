package be.kdg.integration5.checkerscontext.port.in;

public interface GetGameStateUseCase {
    void sendGameStateToPlayer(SendGameStateToPlayerCommand sendGameStateToPlayerCommand);
}
