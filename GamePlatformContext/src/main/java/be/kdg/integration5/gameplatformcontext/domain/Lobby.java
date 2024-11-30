package be.kdg.integration5.gameplatformcontext.domain;

import be.kdg.integration5.gameplatformcontext.domain.exception.LobbyIsFullException;
import be.kdg.integration5.gameplatformcontext.domain.exception.NotAllowedInLobbyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Lobby {
    private LobbyId lobbyId;
    private boolean isPrivate;
    private Game playingGame;
    private Player lobbyOwner;
    private List<Player> players;

    public Lobby(boolean isPrivate, Game playingGame, Player lobbyOwner) {
        this.lobbyId = new LobbyId(UUID.randomUUID());
        this.isPrivate = isPrivate;
        this.playingGame = playingGame;
        this.lobbyOwner = lobbyOwner;
        this.players = new ArrayList<>(List.of(lobbyOwner));
    }

    public void addPlayer(Player player) {
        if (players.contains(player)) return;

        if (players.size() >= playingGame.getMaxPlayers())
            throw new LobbyIsFullException("Lobby [%s] is full".formatted(this.lobbyId.uuid()));

        if (!isPrivate || lobbyOwner.isFriendsWithPlayer(player))
            players.add(player);
        else
            throw new NotAllowedInLobbyException("Player [%s] is not allowed to join this lobby. (Player is not friends with lobby owner)".formatted(player.getPlayerId().uuid()));

    }

    public boolean isFull() {
        return players.size() == playingGame.getMaxPlayers();
    }
}
