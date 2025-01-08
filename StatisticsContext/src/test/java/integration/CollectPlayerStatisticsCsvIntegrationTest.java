package integration;


import be.kdg.integration5.statisticscontext.StatisticsContextApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@ContextConfiguration(classes = { StatisticsContextApplication.class })
@SpringBootTest
@AutoConfigureMockMvc
public class CollectPlayerStatisticsCsvIntegrationTest extends KeycloakTestContainers{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testExportSessionStatisticsCsv() throws Exception {
        String header = getAdminBearer();

        mockMvc.perform(get("/api/v1/players/csv")
                        .header("Authorization", header))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(header().string("Content-Disposition", containsString("attachment; filename=")))
                .andExpect(content().string(containsString("PlayerId"))) // Validate headers
                .andExpect(content().string(containsString("TotalGamesPlayed")))
                .andExpect(content().string(containsString("TotalWins")))
                .andExpect(content().string(containsString("TotalWeekdaysPlayed")))
                .andExpect(content().string(containsString("AvgMoveDuration")));

    }
}
