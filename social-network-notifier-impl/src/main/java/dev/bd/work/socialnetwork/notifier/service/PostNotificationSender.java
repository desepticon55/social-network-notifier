package dev.bd.work.socialnetwork.notifier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;
import java.util.UUID;

/**
 * Post notification sender.
 *
 * @author Alexey Bodyak
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PostNotificationSender {

    private final UserSessionManager sessionManager;

    public void sendMessageToUser(UUID userId, String message) {
        try {
            WebSocketSession session = sessionManager.findUserSession(userId);
            if (Objects.nonNull(session) && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            } else {
                log.error("Session to user = {} is not open", userId);
            }
        } catch (Exception e) {
            log.error("Error during send message to WS to user = {}", userId, e);
            throw new RuntimeException(e);
        }
    }
}
