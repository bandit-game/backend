package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.Player;
import org.springframework.security.oauth2.jwt.Jwt;

public interface RegisterNewPlayerUseCase {
    void registerNewPlayer(String token);
}
