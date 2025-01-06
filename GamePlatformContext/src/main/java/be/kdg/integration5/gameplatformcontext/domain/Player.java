package be.kdg.integration5.gameplatformcontext.domain;

import be.kdg.integration5.gameplatformcontext.domain.exception.NoLobbiesProvidedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private PlayerId playerId;
    private String username;
    private int age;
    private Gender gender;
    private List<Role> roles;
    private List<Player> friends;

    public Player(PlayerId playerId, String username, int age, Gender gender) {
        this.playerId = playerId;
        this.username = username;
        this.age = age;
        this.gender = gender;
    }

    public Lobby findBestLobbyMatch(Collection<Lobby> lobbyList) {
        //TODO
        // It is a very simple implementation of the lobby selection.
        // Might write a more complex one later.
        return lobbyList.stream().findFirst().orElseThrow(
                () -> new NoLobbiesProvidedException("No lobbies were provided to select from.")
        );
    }

    public Lobby createNewPublicLobbyForGame(Game game) {
        return new Lobby(false, game, this);
    }

    public boolean isFriendsWithPlayer(Player player) {
        return this.friends.contains(player);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerId, player.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playerId);
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum Role {
        PLAYER, ADMIN
    }
}
