package dev.bd.work.socialnetwork.notifier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

/**
 * User session web socket handler.
 *
 * @author Alexey Bodyak
 */
@RequiredArgsConstructor
@Component("webSocketHandler")
public class UserSessionWebSocketHandler extends TextWebSocketHandler {

    private final UserSessionManager sessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UUID userId = extractUserId(session);
        sessionManager.registerSession(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UUID userId = extractUserId(session);
        sessionManager.removeSession(userId);
    }

    private UUID extractUserId(WebSocketSession session) {
        String userId = extractQueryParam(session, "userId");
        if (!StringUtils.hasText(userId)) {
            throw new RuntimeException("User id is empty");
        }
        return UUID.fromString(userId);
    }

    private String extractQueryParam(WebSocketSession session, String param) {
        return UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams().getFirst(param);
    }
}
