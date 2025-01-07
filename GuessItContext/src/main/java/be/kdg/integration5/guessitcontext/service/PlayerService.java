package be.kdg.integration5.guessitcontext.service;


import be.kdg.integration5.guessitcontext.domain.Player;

import java.util.List;

public interface PlayerService {
    List<Player> saveAll(List<Player> players);
}
