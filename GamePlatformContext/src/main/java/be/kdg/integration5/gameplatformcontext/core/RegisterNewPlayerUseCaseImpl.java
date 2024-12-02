package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.RegisterNewPlayerUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FindPlayerPort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistPlayerPort;
import be.kdg.integration5.gameplatformcontext.port.out.SendUserInfoPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RegisterNewPlayerUseCaseImpl implements RegisterNewPlayerUseCase {

    private static final Logger log = LoggerFactory.getLogger(RegisterNewPlayerUseCaseImpl.class);
    private final PersistPlayerPort persistPlayerPort;
    private final FindPlayerPort findPlayerPort;
    private final JwtDecoder jwtDecoder;
    private final SendUserInfoPort sendUserInfoPort;

    public RegisterNewPlayerUseCaseImpl(PersistPlayerPort persistPlayerPort, FindPlayerPort findPlayerPort, JwtDecoder jwtDecoder, SendUserInfoPort sendUserInfoPort) {
        this.persistPlayerPort = persistPlayerPort;
        this.findPlayerPort = findPlayerPort;
        this.jwtDecoder = jwtDecoder;
        this.sendUserInfoPort = sendUserInfoPort;
    }

    @Override
    public void registerNewPlayer(String token) {
        // parse JWT
        Jwt decodedJwt = jwtDecoder.decode(token);
        String userId = decodedJwt.getSubject();
        Map<String, Object> claims = decodedJwt.getClaims();

        PlayerId playerId = new PlayerId(UUID.fromString(userId));
        if (!findPlayerPort.playerExists(playerId)) {
            log.info(claims.toString());
            String username = (String) claims.getOrDefault("preferred_username", "");
            String gender = (String) claims.getOrDefault("gender", "");
            String country = (String) claims.getOrDefault("country", "");
            String city = (String) claims.getOrDefault("city", "");
            int age = (int) (long) claims.getOrDefault("age", 0);
            Player player = new Player(playerId, username, age, Player.Gender.valueOf(gender.toUpperCase()));
            persistPlayerPort.save(player);
            sendUserInfoPort.sendUserInfo(player, country, city);
            log.info("Registered new player '{}' with id '{}'", username, playerId);
        }


    }
}
