package order.processing.system.service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.config.KafkaProperties;
import order.processing.system.service.dto.NotificationDto;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationProducer {
    private final KafkaProperties kafkaProperties;
    private final Producer<String, NotificationDto> producer;

    public void send(NotificationDto notificationDto) {
        ProducerRecord<String, NotificationDto> record =
                new ProducerRecord<>(
                        kafkaProperties.getTopic(),
                        notificationDto
                );

        try {
            producer.send(record).get();
        } catch (InterruptedException ex) {
            log.warn("Await interrupted", ex);
            Thread.currentThread().interrupt();
        } catch (ExecutionException ex) {
            if (ex.getCause() instanceof RuntimeException) {
                throw (RuntimeException) ex.getCause();
            } else {
                throw new RuntimeException("Unable to send message in kafka", ex.getCause());
            }
        }
    }
}
