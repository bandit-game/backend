package be.kdg.integration5.gameplatformcontext.adapter.out.web.chatbot;

import be.kdg.integration5.gameplatformcontext.adapter.out.web.exception.ChatbotRequestFailedException;
import be.kdg.integration5.gameplatformcontext.port.out.FetchChatbotPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class FetchChatbotAdapter implements FetchChatbotPort {

    private final WebClient webClient;

    @Value("${chatbot.url}")
    private String chatbotUrl;

    public FetchChatbotAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public String sendQuery(String query) {
        try {
            return webClient.post()
                    .uri(chatbotUrl + "/chat")
                    .bodyValue(new ChatbotRequest(query))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ChatbotRequestFailedException("Chatbot returned an error: " + e.getMessage());
        } catch (WebClientRequestException e) {
            throw new ChatbotRequestFailedException("Chatbot is unreachable: " + e.getMessage());
        }
    }
}
