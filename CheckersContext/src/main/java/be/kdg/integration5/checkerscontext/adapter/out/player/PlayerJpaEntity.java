package be.kdg.integration5.checkerscontext.adapter.out.player;

import be.kdg.integration5.checkerscontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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

    @ManyToMany(mappedBy = "players", fetch = FetchType.LAZY)
    private List<GameJpaEntity> games;

    public PlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public static PlayerJpaEntity of(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().uuid(),
                player.getName()
        );
    }

    public Player toDomain() {
        return new Player(
                new PlayerId(playerId),
                this.name
        );
    }
}
