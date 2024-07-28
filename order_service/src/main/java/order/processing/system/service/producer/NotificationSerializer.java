package order.processing.system.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.dto.NotificationDto;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationSerializer implements Serializer<NotificationDto> {
    private final ObjectMapper objectMapper;

    @Override
    public byte[] serialize(String topic, NotificationDto data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException ex) {
            log.error("Unable to serialize message: {}", data, ex);
            return new byte[0];
        }
    }
}
