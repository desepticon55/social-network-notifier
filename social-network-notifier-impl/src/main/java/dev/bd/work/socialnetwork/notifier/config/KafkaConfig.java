package dev.bd.work.socialnetwork.notifier.config;

import static dev.khbd.interp4j.core.Interpolations.s;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Kafka config.
 *
 * @author Alexey Bodyak
 */
@Configuration
public class KafkaConfig {

    @Bean("postListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> postListenerContainerFactory(
            @Qualifier("postConsumerFactory") ConsumerFactory<String, String> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(1);
        factory.setBatchListener(Boolean.FALSE);
        return factory;
    }

    @Bean("postConsumerFactory")
    public ConsumerFactory<String, String> postConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        UUID consumerGroupId = UUID.randomUUID();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, s("${kafkaProperties.getConsumer().getGroupId()}_${consumerGroupId}"));
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new StringDeserializer()
        );
    }
}
