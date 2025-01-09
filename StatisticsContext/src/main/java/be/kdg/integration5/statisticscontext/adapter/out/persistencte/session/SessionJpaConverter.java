package be.kdg.integration5.statisticscontext.adapter.out.persistencte.session;

import be.kdg.integration5.statisticscontext.adapter.out.persistencte.game.GameJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.move.MoveJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.player.PlayerJpaConverter;
import be.kdg.integration5.statisticscontext.domain.PlayerActivity;
import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SessionJpaConverter {

    private final PlayerJpaConverter playerJpaConverter;
    private final GameJpaConverter gameJpaConverter;
    private final MoveJpaConverter moveJpaConverter;

    public SessionJpaConverter(PlayerJpaConverter playerJpaConverter, GameJpaConverter gameJpaConverter, MoveJpaConverter moveJpaConverter) {
        this.playerJpaConverter = playerJpaConverter;
        this.gameJpaConverter = gameJpaConverter;
        this.moveJpaConverter = moveJpaConverter;
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
                                playerJpaConverter.toDomain(player.getPlayer()),
                                player.getMoves()
                                        .stream()
                                        .map(moveJpaConverter::toDomain)
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()),
                entity.getGame() == null ? null : gameJpaConverter.toDomain(entity.getGame()),
                entity.getWinner() == null ? null : playerJpaConverter.toDomain(entity.getWinner())
        );
    }

    public void updateValues(SessionJpaEntity sessionJpaEntity, Session session) {
        sessionJpaEntity.setDraw(session.isDraw());
        sessionJpaEntity.setFinishTime(session.getFinishTime());
    }
}
