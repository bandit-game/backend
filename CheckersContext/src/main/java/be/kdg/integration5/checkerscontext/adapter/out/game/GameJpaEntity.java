package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameJpaEntity {
    @Id
    @Column(name = "game_id", nullable = false, unique = true, updatable = false)
    private UUID gameId;

    @Column(nullable = false)
    private boolean isFinished;

    @OneToMany(mappedBy = "game")
    private Set<PieceJpaEntity> pieces;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "games_players",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    )
    private Set<PlayerJpaEntity> players;

    @ManyToOne
    private PlayerJpaEntity currentPlayer;

    public GameJpaEntity(UUID gameId, boolean isFinished, Set<PlayerJpaEntity> players, PlayerJpaEntity currentPlayer) {
        this.gameId = gameId;
        this.isFinished = isFinished;
        this.players = players;
        this.currentPlayer = currentPlayer;
    }

    public GameJpaEntity(UUID gameId, boolean isFinished) {
        this.gameId = gameId;
        this.isFinished = isFinished;
    }
}
