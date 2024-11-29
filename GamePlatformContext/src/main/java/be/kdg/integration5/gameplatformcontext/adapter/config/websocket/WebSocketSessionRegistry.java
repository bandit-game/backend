package be.kdg.integration5.gameplatformcontext.adapter.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void registerSession(String userId, WebSocketSession session) {
        if (userSessions.containsKey(userId)) {
            logger.warn("Session already exists for user {}, ignoring new connection", userId);
        } else {
            logger.info("Registering session for user {}", userId);
            userSessions.put(userId, session);
        }
    }

    public void removeSession(String userId) {
        userSessions.remove(userId);
    }

    public WebSocketSession getSession(String userId) {
        return userSessions.get(userId);
    }
}
