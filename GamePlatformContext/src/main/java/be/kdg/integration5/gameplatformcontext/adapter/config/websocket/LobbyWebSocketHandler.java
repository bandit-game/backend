package be.kdg.integration5.gameplatformcontext.adapter.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Deprecated
public class LobbyWebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(LobbyWebSocketHandler.class);
    private final JwtDecoder jwtDecoder;

    public LobbyWebSocketHandler(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract JWT token from headers
        String authHeader = session.getHandshakeHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // Get the JWT token

            // Decode and validate the JWT token
            Jwt decodedJwt = jwtDecoder.decode(token);

            String userId = decodedJwt.getSubject();  // Get the user ID from the decoded JWT token
            //webSocketSessionRegistry.registerSession(userId, session);
            session.getAttributes().put("user_id", userId);
            logger.info("Registered session for user {}", userId);
        } else {
            logger.info("Failed to decode authorization header");
        }


    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages here
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("user_id");

        if (userId != null) {
            // Remove the session from the registry using the user ID
            //webSocketSessionRegistry.removeSession(userId);
            logger.info("Removed session for user {}", userId);
        }
        else {
            logger.info("Failed to identify user_id in session attributes");
        }
    }
}
