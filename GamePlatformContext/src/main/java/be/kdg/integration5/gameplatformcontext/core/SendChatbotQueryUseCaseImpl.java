package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.port.in.SendChatbotQueryUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.ChatbotPort;
import org.springframework.stereotype.Service;

@Service
public class SendChatbotQueryUseCaseImpl implements SendChatbotQueryUseCase {

    private final ChatbotPort chatbotPort;

    public SendChatbotQueryUseCaseImpl(ChatbotPort chatbotPort) {
        this.chatbotPort = chatbotPort;
    }

    @Override
    public String processQuery(String query) {
        return chatbotPort.sendQuery(query);
    }
}
