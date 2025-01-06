package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.port.in.SendChatbotQueryUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FetchChatbotPort;
import org.springframework.stereotype.Service;

@Service
public class SendChatbotQueryUseCaseImpl implements SendChatbotQueryUseCase {

    private final FetchChatbotPort fetchChatbotPort;

    public SendChatbotQueryUseCaseImpl(FetchChatbotPort fetchChatbotPort) {
        this.fetchChatbotPort = fetchChatbotPort;
    }

    @Override
    public String processQuery(String query) {
        return fetchChatbotPort.sendQuery(query);
    }
}
