package order.processing.system.service.service;

import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService implements NotificationSender<NotificationDto> {

//    @Value("${sendgrid.api.key}")
//    private String sendGridApiKey;

    @Override
    public void send(NotificationDto notification) {

    }
}
