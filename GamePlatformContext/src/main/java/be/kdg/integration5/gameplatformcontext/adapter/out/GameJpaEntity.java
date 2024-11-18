package be.kdg.integration5.gameplatformcontext.adapter.out;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Price;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Currency;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double priceAmount;

    @Column(nullable = false)
    private String currencyCode;

    @OneToMany(mappedBy = "game")
    private List<AchievementJpaEntity> achievements;

    public Game toDomain() {
        return new Game(
                new GameId(this.gameId),
                this.title,
                this.description,
                new Price(this.priceAmount, Currency.getInstance(this.currencyCode)),
                new ArrayList<>(this.achievements.stream().map(AchievementJpaEntity::toDomain).toList())
        );
    }
}
