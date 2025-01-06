package core;


import be.kdg.integration5.statisticscontext.StatisticsContextApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@ContextConfiguration(classes = { StatisticsContextApplication.class })
@SpringBootTest
@AutoConfigureMockMvc
public class CollectPlayerStatisticsCsvIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "admin")
    void testExportSessionStatisticsCsv() throws Exception {
        mockMvc.perform(get("/api/players/export/csv"))
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
