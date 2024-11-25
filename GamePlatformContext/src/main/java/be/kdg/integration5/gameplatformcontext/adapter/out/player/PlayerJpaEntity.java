package be.kdg.integration5.gameplatformcontext.adapter.out.player;

import be.kdg.integration5.gameplatformcontext.adapter.out.achievement.AchievementJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.lobby.LobbyJpaEntity;
import be.kdg.integration5.gameplatformcontext.domain.Achievement;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    @Column(nullable = false, unique = true, updatable = false)
    private UUID playerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Player.Gender gender;

    //TODO Add roles
//    private List<Player.Role> roles;

    @OneToMany(mappedBy = "lobbyOwner")
    private List<LobbyJpaEntity> ownsLobbies;

    @ManyToMany(mappedBy = "players")
    private List<LobbyJpaEntity> lobbies;

    @ManyToMany
    private List<AchievementJpaEntity> earnedAchievements;

    @ManyToMany
    private List<PlayerJpaEntity> friends;

    private Player toSimpleDomain() {
        return new Player(
                new PlayerId(this.playerId),
                this.username,
                this.age,
                this.gender
        );
    }

    public Player toDomain() {
        return new Player(
                new PlayerId(this.playerId),
                this.username,
                this.age,
                this.gender,
                //TODO Add list of roles
                new ArrayList<>(),
                new ArrayList<>(this.earnedAchievements.stream().map(AchievementJpaEntity::toDomain).toList()),
                new ArrayList<>(this.friends.stream().map(PlayerJpaEntity::toSimpleDomain).toList())
        );
    }
}
