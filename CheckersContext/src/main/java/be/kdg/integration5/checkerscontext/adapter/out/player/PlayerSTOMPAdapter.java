package be.kdg.integration5.checkerscontext.adapter.out.player;

import be.kdg.integration5.checkerscontext.adapter.out.dto.GameStateResponseDto;
import be.kdg.integration5.checkerscontext.adapter.out.dto.MoveGetDto;
import be.kdg.integration5.checkerscontext.adapter.out.dto.PossibleMovesForPlayerResponseDto;
import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.out.NotifyPlayerPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerSTOMPAdapter implements NotifyPlayerPort {

    private final SimpMessagingTemplate messagingTemplate;

    public PlayerSTOMPAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyPossibleMovesForPlayer(List<Move> moves, Player player) {
        PossibleMovesForPlayerResponseDto payloadDTO = new PossibleMovesForPlayerResponseDto(
                player.getPlayerId().uuid(),
                moves.stream().map(MoveGetDto::of).toList()
        );
        messagingTemplate.convertAndSend(getUserQueue(player.getPlayerId()), payloadDTO);
    }

    @Override
    public void notifyPlayerOfGameState(PlayerId playerId, Game game) {
        GameStateResponseDto gameStateResponseDto = GameStateResponseDto.of(game);
        messagingTemplate.convertAndSend(getUserQueue(playerId), gameStateResponseDto);
    }

    @Override
    public void notifyAllPlayersWithGameState(Game game) {
        GameStateResponseDto gameStateResponseDto = GameStateResponseDto.of(game);
        game.getPlayers().forEach(
                player -> messagingTemplate.convertAndSend(getUserQueue(player.getPlayerId()), gameStateResponseDto));
    }

    private String getUserQueue(PlayerId playerId) {
        return "/queue/user/" + playerId.uuid().toString();
    }

}
