package dev.bd.work.socialnetwork.notifier.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

/**
 * Post dto.
 *
 * @author Alexey Bodyak
 */
@Data
@Accessors(chain = true)
public class PostDto {
    private UUID postId;
    private UUID userId;
    private String content;
    private UUID authorId;
    private Instant createdAt;
}
