package order.processing.system.service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.dto.NotificationDto;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationDeserializer implements Deserializer<NotificationDto> {
    private final ObjectMapper objectMapper;

    @Override
    public NotificationDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, NotificationDto.class);
        } catch (IOException ex) {
            String message = new String(data, StandardCharsets.UTF_8);
            log.error("Unable to deserialize message {}", message, ex);
            return null;
        }
    }
}
