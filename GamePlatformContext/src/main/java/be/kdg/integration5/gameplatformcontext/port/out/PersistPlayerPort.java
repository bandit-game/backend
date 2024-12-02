package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Player;

public interface PersistPlayerPort {
    Player save(Player player);
}
