package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Square;

public interface FindSquarePort {
    Square findSquareByGameIdAndXAndY(GameId gameId, int x, int y);
}
