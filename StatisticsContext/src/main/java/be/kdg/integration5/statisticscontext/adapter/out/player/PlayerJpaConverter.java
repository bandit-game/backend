package be.kdg.integration5.statisticscontext.adapter.out.player;

import be.kdg.integration5.statisticscontext.domain.Location;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerJpaConverter {

    public PlayerJpaEntity toJpa(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().uuid(),
                player.getLocation().city(),
                player.getLocation().country(),
                player.getGender().name(),
                player.getAge(),
                player.getPlayerName()
        );
    }

    public Player toDomain(PlayerJpaEntity entity) {
        return new Player(entity.getPlayerName(),
                new PlayerId(entity.getPlayerId()),
                entity.getAge(),
                Player.Gender.valueOf(entity.getGender()),
                new Location(entity.getCountry(), entity.getCity()));
    }

}
