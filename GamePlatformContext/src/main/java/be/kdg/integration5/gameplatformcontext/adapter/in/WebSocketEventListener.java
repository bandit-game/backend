package be.kdg.integration5.gameplatformcontext.adapter.in;

import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.LeaveTheLobbyUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private final JwtDecoder jwtDecoder;
    private final ConcurrentHashMap<String, String> sessionUserMap = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LeaveTheLobbyUseCase leaveTheLobbyUseCase;


    public WebSocketEventListener(JwtDecoder jwtDecoder, LeaveTheLobbyUseCase leaveTheLobbyUseCase) {
        this.jwtDecoder = jwtDecoder;
        this.leaveTheLobbyUseCase = leaveTheLobbyUseCase;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Extract Authorization header
        String authHeader = headerAccessor.getFirstNativeHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                Jwt decodedJwt = jwtDecoder.decode(token);
                String userId = decodedJwt.getSubject();

                String sessionId = headerAccessor.getSessionId();
                sessionUserMap.put(sessionId, userId);


                logger.info("User connected with ID: {}", userId);
            } catch (Exception e) {
                logger.error("Failed to decode JWT: {}", e.getMessage());
            }
        } else {
            logger.error("Authorization header is missing or invalid");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Get session ID and remove from map
       String sessionId = headerAccessor.getSessionId();
       if (sessionId != null) {
           String userId = sessionUserMap.remove(sessionId);
           leaveTheLobbyUseCase.removePlayerFromLobby(new PlayerId(UUID.fromString(userId)));
           logger.info("User disconnected with ID: {}", userId);
       }

    }
}
