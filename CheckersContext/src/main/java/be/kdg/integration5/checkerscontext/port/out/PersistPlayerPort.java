package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.Player;

import java.util.List;

public interface PersistPlayerPort {
    Player save(Player player);
    List<Player> saveAll(List<Player> players);
}
