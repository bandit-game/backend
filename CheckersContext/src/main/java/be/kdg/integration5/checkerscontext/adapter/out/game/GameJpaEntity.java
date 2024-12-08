package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.board.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private LocalDateTime startedTime;
    private LocalDateTime finishedTime;

    @OneToOne(mappedBy = "game")
    @PrimaryKeyJoinColumn
    private BoardJpaEntity board;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "games_players",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    )
    private List<PlayerJpaEntity> players;

    public static GameJpaEntity of(Game game) {
        return new GameJpaEntity(
                game.getPlayedMatchId().uuid(),
                game.getStartedTime(),
                game.getFinishedTime(),
                BoardJpaEntity.of(game.getBoard()),
                new ArrayList<>(game.getPlayers().stream().map(PlayerJpaEntity::of).toList())
        );
    }

    public Game toDomain() {
        return new Game(
                new GameId(this.gameId),
                this.startedTime,
                this.finishedTime,
//                this.board.toDomain(),
                new ArrayList<>(this.players.stream().map(PlayerJpaEntity::toDomain).toList())
        );
    }
}
