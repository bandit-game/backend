package be.kdg.integration5.statisticscontext.adapter.out.game;

import be.kdg.integration5.statisticscontext.adapter.out.session.SessionJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "game_id")
    private UUID gameId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "game")
    private List<SessionJpaEntity> sessions;

    public GameJpaEntity(UUID gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }
}
