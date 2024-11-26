package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
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
    @Column(nullable = false, unique = true, updatable = false)
    private UUID gameId;

    private LocalDateTime startedTime;
    private LocalDateTime finishedTime;

    @OneToOne
    private BoardJpaEntity board;

    @OneToMany
    private List<PlayerJpaEntity> players;

    public static GameJpaEntity of(Game game) {
        return new GameJpaEntity(
                game.getPlayedMatchId().uuid(),
                game.getStartedTime(),
                game.getFinishedTime(),
                BoardJpaEntity.of(game.getBoard(), game.getPlayedMatchId()),
                new ArrayList<>(game.getPlayers().stream().map(PlayerJpaEntity::of).toList())
        );
    }

    public Game toDomain() {
        return new Game(
                new GameId(this.gameId),
                this.startedTime,
                this.finishedTime,
                this.board.toDomain()
        );
    }
}
