package dev.bd.work.socialnetwork.notifier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User session manager.
 *
 * @author Alexey Bodyak
 */
@Component
@RequiredArgsConstructor
public class UserSessionManager {

    private final Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(UUID userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void removeSession(UUID userId) {
        sessions.remove(userId);
    }

    public WebSocketSession findUserSession(UUID userId) {
        return sessions.get(userId);
    }
}
