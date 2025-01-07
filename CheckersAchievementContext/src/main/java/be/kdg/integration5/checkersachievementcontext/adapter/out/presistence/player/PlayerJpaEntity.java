package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.game.GameJpaEntity;
import be.kdg.integration5.checkersachievementcontext.domain.Player;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
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

    @ManyToMany(mappedBy = "players", fetch = FetchType.LAZY)
    private List<GameJpaEntity> games;

    public PlayerJpaEntity(UUID playerId) {
        this.playerId = playerId;
    }

    public static PlayerJpaEntity of(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().uuid()
        );
    }

    public Player toDomain() {
        return new Player(
                new PlayerId(playerId)
        );
    }
}
