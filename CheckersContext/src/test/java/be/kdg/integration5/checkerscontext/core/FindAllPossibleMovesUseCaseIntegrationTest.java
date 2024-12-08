package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.adapter.in.dto.GetMovesRequestDTO;
import be.kdg.integration5.checkerscontext.adapter.in.dto.PossibleMovesForPlayerDTO;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import java.lang.reflect.Type;

import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FindAllPossibleMovesUseCaseIntegrationTest {

    private WebSocketStompClient stompClient;

    private BlockingQueue<PossibleMovesForPlayerDTO> receivedMessages;

    private static final String WS_URI = "ws://localhost:8042/ws";
    private static final String SUBSCRIBE_DESTINATION = "/queue/user/";
    private static final String SEND_DESTINATION = "/app/get-moves";

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        receivedMessages = new LinkedBlockingQueue<>();
    }

    @Test
    public void testFindAllPossibleMovesUseCase() throws Exception {
        // Connect to WebSocket
        StompSession stompSession = stompClient
                .connect(WS_URI, new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        UUID gameId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID playerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        // Subscribe to the player's queue
        stompSession.subscribe(SUBSCRIBE_DESTINATION + playerId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return PossibleMovesForPlayerDTO.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                receivedMessages.add((PossibleMovesForPlayerDTO) payload);
            }
        });

        // Send a request to the "get-moves" mapping
        GetMovesRequestDTO requestDTO = new GetMovesRequestDTO(gameId, 6, 3);
        stompSession.send(SEND_DESTINATION, requestDTO);

        // Wait and verify the response
        PossibleMovesForPlayerDTO response = receivedMessages.poll(5, TimeUnit.SECONDS);
        assertThat(response).isNotNull();
        assertThat(response.playerId()).isEqualTo(playerId);
        assertThat(response.moves().size()).isNotEqualTo(0);
    }
}
