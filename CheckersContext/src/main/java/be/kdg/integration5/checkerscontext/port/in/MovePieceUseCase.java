package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.Game;

public interface MovePieceUseCase {
    Game movePiece(MovePieceCommand movePieceCommand);
}
