package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.port.in.GetPlayersByUserNameUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FindPlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GetPlayersByUsernameUseCaseImpl implements GetPlayersByUserNameUseCase {

    private final FindPlayerPort findPlayerPort;

    public GetPlayersByUsernameUseCaseImpl(FindPlayerPort findPlayerPort) {
        this.findPlayerPort = findPlayerPort;
    }

    @Override
    public List<Player> getPlayers(String username) {
        return findPlayerPort.findByUsername(username);
    }
}
