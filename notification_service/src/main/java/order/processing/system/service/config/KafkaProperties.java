package order.processing.system.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@Validated
public class KafkaProperties {
    @Value("${spring.kafka.bootstrap-servers}")
    private List<String> bootstrapServers = new ArrayList<>();

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${spring.kafka.topics.notification-publication}")
    private String topic;
}
