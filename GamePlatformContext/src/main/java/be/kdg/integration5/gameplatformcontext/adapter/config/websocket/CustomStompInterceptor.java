package be.kdg.integration5.gameplatformcontext.adapter.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.WebSocketSession;

@Deprecated
public class CustomStompInterceptor implements ChannelInterceptor {
    private final JwtDecoder jwtDecoder;
    private final Logger logger = LoggerFactory.getLogger(CustomStompInterceptor.class);

    public CustomStompInterceptor(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // Extract and validate JWT token from the message headers

        final StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {

            String jwt = parseJwt(accessor);
            if (jwt != null) {
                Jwt decodedJwt = jwtDecoder.decode(jwt);

                String userId = decodedJwt.getSubject();
                Object sessionObject = message.getHeaders().get("simpSessionAttributes");
                if (sessionObject instanceof WebSocketSession session) {
                    //webSocketSessionRegistry.registerSession(userId, session);
                    session.getAttributes().put("user_id", userId);
                    logger.info("Registered session for user {}", userId);
                } else {
                    logger.error("Expected WebSocketSession, but got: {}", sessionObject.getClass().getName());
                }

            }
            else {
                logger.error("Failed to parse JWT");
            }
        }
        return message;
    }

    public String parseJwt(StompHeaderAccessor accessor) {
        String token = accessor.getFirstNativeHeader("Authorization");
        String jwt = null;
        if (token != null) {
            jwt = token.substring(7);
        }
        return jwt;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        // Custom logic after the message has been sent (optional)
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        // Custom cleanup after sending message (optional)
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        // Custom cleanup after receiving message (optional)
    }



}
