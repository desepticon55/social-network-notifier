package dev.bd.work.socialnetwork.notifier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bd.work.socialnetwork.notifier.dto.PostDomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Post domain event consumer.
 *
 * @author Alexey Bodyak
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PostDomainEventConsumer {

    private final ObjectMapper objectMapper;
    private final PostNotificationSender sender;

    @KafkaListener(
            containerFactory = "postListenerContainerFactory",
            topics = "${social-network.post.topic}"
    )
    public void handle(@Payload String payload) {
        try {
            log.debug("Received post domain event: {}", payload);
            PostDomainEvent post = objectMapper.readValue(payload, PostDomainEvent.class);
            sender.sendMessageToUser(post.getUserId(), payload);
        } catch (JsonProcessingException e) {
            log.error("Payload has wrong structure. Skip message", e);
        }
    }
}
