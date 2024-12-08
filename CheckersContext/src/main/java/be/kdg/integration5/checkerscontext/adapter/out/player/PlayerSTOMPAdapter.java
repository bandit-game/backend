package be.kdg.integration5.checkerscontext.adapter.out.player;

import be.kdg.integration5.checkerscontext.adapter.in.dto.MoveGetDto;
import be.kdg.integration5.checkerscontext.adapter.in.dto.PossibleMovesForPlayerDTO;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Player;
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
        PossibleMovesForPlayerDTO payloadDTO = new PossibleMovesForPlayerDTO(
                player.getPlayerId().uuid(),
                moves.stream().map(MoveGetDto::of).toList()
        );
        messagingTemplate.convertAndSend(getUserQueue(player), payloadDTO);
    }

    private String getUserQueue(Player player) {
        return "/queue/user/" + player.getPlayerId().uuid().toString();
    }

}
