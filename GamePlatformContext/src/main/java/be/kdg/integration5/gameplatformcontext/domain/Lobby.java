package be.kdg.integration5.gameplatformcontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Lobby {
    private LobbyId lobbyId;
    private boolean isPrivate;
}
