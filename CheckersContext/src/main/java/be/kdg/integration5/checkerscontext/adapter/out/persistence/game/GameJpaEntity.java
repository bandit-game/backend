package be.kdg.integration5.checkerscontext.adapter.out.persistence.game;

import be.kdg.integration5.checkerscontext.adapter.out.persistence.piece.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.persistence.player.PlayerJpaEntity;
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

    @Column(nullable = false)
    private boolean isDraw;

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "player_id")
    private PlayerJpaEntity winner;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
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

    public GameJpaEntity(UUID gameId, boolean isFinished, boolean isDraw, PlayerJpaEntity winner, Set<PlayerJpaEntity> players, PlayerJpaEntity currentPlayer) {
        this.gameId = gameId;
        this.isFinished = isFinished;
        this.isDraw = isDraw;
        this.winner = winner;
        this.players = players;
        this.currentPlayer = currentPlayer;
    }

    public GameJpaEntity(UUID gameId, boolean isFinished) {
        this.gameId = gameId;
        this.isFinished = isFinished;
    }
}
