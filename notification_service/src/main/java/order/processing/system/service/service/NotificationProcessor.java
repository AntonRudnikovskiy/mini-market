package order.processing.system.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.dto.NotificationDto;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationProcessor {
    private final EmailService emailService;

    public void notificationProcessor(NotificationDto notificationDto) {
        emailService.send(notificationDto);
        log.info("sendsendsendsend {}", notificationDto);
    }
}
