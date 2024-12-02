package be.kdg.integration5.gameplatformcontext.adapter.in.messaging;

import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.port.out.SendUserInfoPort;
import org.springframework.stereotype.Component;

@Component
public class StatisticsPublisher implements SendUserInfoPort {
    @Override
    public void sendUserInfo(Player player, String country, String city) {

    }
}
