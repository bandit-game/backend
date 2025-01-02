package be.kdg.integration5.statisticscontext.adapter.out.session;

import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaConverter;
import be.kdg.integration5.statisticscontext.domain.PlayerActivity;
import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SessionJpaConverter {

    private final PlayerJpaConverter playerJpaConverter;
    private final GameJpaConverter gameJpaConverter;

    public SessionJpaConverter(PlayerJpaConverter playerJpaConverter, GameJpaConverter gameJpaConverter) {
        this.playerJpaConverter = playerJpaConverter;
        this.gameJpaConverter = gameJpaConverter;
    }

    public SessionJpaEntity toJpa(Session session) {
        return new SessionJpaEntity(
                session.getStartTime(),
                session.getSessionId().uuid(),
                session.getFinishTime(),
                session.isDraw()
        );
    }

    public Session toDomain(SessionJpaEntity entity) {
        return new Session(
                new SessionId(entity.getSessionId()),
                entity.getStartTime(),
                entity.getFinishTime(),
                entity.isDraw(),
                entity.getPlayers()
                        .stream()
                        .map(player -> new PlayerActivity(
                                playerJpaConverter.toDomain(player.getPlayer())
                        )).collect(Collectors.toList()),
                gameJpaConverter.toDomain(entity.getGame()),
                entity.getWinner() == null ? null : playerJpaConverter.toDomain(entity.getWinner())
        );
    }
}
