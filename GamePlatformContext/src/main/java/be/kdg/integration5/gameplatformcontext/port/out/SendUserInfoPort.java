package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Player;

public interface SendUserInfoPort {
    void sendUserInfo(Player player, String country, String city);
}
