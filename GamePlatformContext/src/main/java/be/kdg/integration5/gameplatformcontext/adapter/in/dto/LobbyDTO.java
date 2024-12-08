package be.kdg.integration5.gameplatformcontext.adapter.in.dto;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record LobbyDTO(UUID lobbyId, List<PlayerDTO> players, boolean isPrivate, int lobbyMaxSize) {
    public LobbyDTO {
        Objects.requireNonNull(lobbyId);
        Objects.requireNonNull(players);
    }

    public static LobbyDTO of(Lobby lobby) {
        return new LobbyDTO(
                lobby.getLobbyId().uuid(),
                lobby.getPlayers().stream().map(PlayerDTO::of).toList(),
                lobby.isPrivate(),
                lobby.getPlayingGame().getMaxLobbyPlayersAmount()
        );
    }
}
