package be.kdg.integration5.guessitcontext.service;

import be.kdg.integration5.guessitcontext.domain.Player;
import be.kdg.integration5.guessitcontext.reposiotory.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        return playerRepository.saveAll(players);
    }

}
