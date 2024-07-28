package order.processing.system.service.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.config.KafkaProperties;
import order.processing.system.service.dto.NotificationDto;
import order.processing.system.service.service.NotificationProcessor;
import order.processing.system.service.utils.GracefullyShutdownStartEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationKafkaConsumer {
    public static final int MAX_MESSAGE_PROCESS_RETRY = 600;
    private final Consumer<String, NotificationDto> consumer;
    private final NotificationProcessor processor;
    private final KafkaProperties properties;

    private final Thread consumerThread = new Thread(
            this::runConsumer,
            this.getClass().getSimpleName()
    );

    private volatile boolean exitFlag = true;

    @EventListener
    public void startEventCycle(ContextRefreshedEvent event) {
        consumerThread.start();
    }

    @EventListener
    public void stopEventCycle(GracefullyShutdownStartEvent gracefullyShutdownStartEvent) {
        exitFlag = false;
        consumer.wakeup();
    }

    void runConsumer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        try {
            consumer.subscribe(List.of(properties.getTopic()));
            while (exitFlag) {
                final ConsumerRecords<String, NotificationDto> consumerRecords = consumer.poll(Duration.ofMillis(5000));
                boolean messageProcessingNotFinished;
                int failCount = 0;
                do {
                    try {
                        processMessages(consumerRecords);
                        messageProcessingNotFinished = false;
                    } catch (Exception ex) {
                        messageProcessingNotFinished = true;
                        failCount++;
                        if (failCount > MAX_MESSAGE_PROCESS_RETRY) {
                            log.error("Unable to process any message after {} retry", MAX_MESSAGE_PROCESS_RETRY, ex);
                            System.exit(13);
                        } else {
                            log.warn("Unable to process messages", ex);
                            scheduler.schedule(() -> {
                            }, 1, TimeUnit.SECONDS).get();
                        }
                    }
                } while (messageProcessingNotFinished);
                consumer.commitAsync();
            }
        } catch (InterruptedException ex) {
            log.error("{} thread execution interrupted", getClass().getSimpleName(), ex);
            exitFlag = false;
        } catch (WakeupException ex) {
            log.error("{} thread finish execution", getClass().getSimpleName(), ex);
        } catch (Exception ex) {
            log.error("kafka internal error when fetching records");
            System.exit(13);
        } finally {
            consumer.unsubscribe();
        }
    }

    private void processMessages(ConsumerRecords<String, NotificationDto> consumerRecords) {
        log.debug("Records fetched {}", consumerRecords.count());
        for (ConsumerRecord<String, NotificationDto> record : consumerRecords) {
            NotificationDto value = record.value();
            if (value != null) {
                processor.notificationProcessor(value);
            }
        }
    }
}
