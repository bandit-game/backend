package be.kdg.integration5.gameplatformcontext.core;
import be.kdg.integration5.gameplatformcontext.GamePlatformContextApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = { GamePlatformContextApplication.class })
@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testChatbotQueryReturnsBadGatewayWhenServiceFails() throws Exception {

        // Act & Assert: Send a POST request and expect 502 status
        mockMvc.perform(post("/api/v1/chat/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"query\": \"Hello chatbot!\"}")) // JSON payload
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.status").value(502)) // Check HTTP status
                .andExpect(jsonPath("$.detail").value("Chatbot is unreachable: Connection refused: localhost/[0:0:0:0:0:0:0:1]:8000")) // Match the error detail
                .andExpect(jsonPath("$.title").value("Bad Gateway")); // Match the title
    }
}
