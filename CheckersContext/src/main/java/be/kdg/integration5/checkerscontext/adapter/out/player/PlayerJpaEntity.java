package be.kdg.integration5.checkerscontext.adapter.out.player;

import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerJpaEntity {
    @Id
    @Column(name = "player_id", nullable = false, unique = true, updatable = false)
    private UUID playerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isFirst;

    public static PlayerJpaEntity of(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().uuid(),
                player.getName(),
                player.isFirst()
        );
    }

    public Player toDomain() {
        return new Player(
                new PlayerId(playerId),
                this.name,
                this.isFirst
        );
    }
}
