package be.kdg.integration5.gameplatformcontext.adapter.out.game;

import be.kdg.integration5.gameplatformcontext.adapter.out.achievement.AchievementJpaEntity;
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

    @Column(nullable = false)
    private int maxLobbyPlayerAmount;

    @Column(nullable = false)
    private String frontendUrl;

    @Column(nullable = false)
    private String backendUrl;

    @Column(nullable = false)
    private String gameImageUrl;

    @OneToMany(mappedBy = "game")
    private List<AchievementJpaEntity> achievements;

    public GameJpaEntity(UUID gameId, String title, String description, double priceAmount, String currencyCode, int maxLobbyPlayerAmount, String frontendUrl, String backendUrl, String gameImageUrl) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.priceAmount = priceAmount;
        this.currencyCode = currencyCode;
        this.maxLobbyPlayerAmount = maxLobbyPlayerAmount;
        this.frontendUrl = frontendUrl;
        this.backendUrl = backendUrl;
        this.gameImageUrl = gameImageUrl;
    }

    public Game toDomain() {
        return new Game(
                new GameId(this.gameId),
                this.title,
                this.description,
                new Price(this.priceAmount, Currency.getInstance(this.currencyCode)),
                this.maxLobbyPlayerAmount,
                this.achievements != null ? new ArrayList<>(this.achievements.stream().map(AchievementJpaEntity::toDomain).toList()) : new ArrayList<>(),
                this.frontendUrl,
                this.backendUrl,
                this.gameImageUrl

        );
    }

    public static GameJpaEntity of(Game game) {
        return new GameJpaEntity(
                game.getGameId().uuid(),
                game.getTitle(),
                game.getDescription(),
                game.getPrice().amount(),
                game.getPrice().currency().getCurrencyCode(),
                game.getMaxLobbyPlayersAmount(),
                game.getFrontendUrl(),
                game.getBackendUrl(),
                game.getGameImageUrl()
        );
    }
}
