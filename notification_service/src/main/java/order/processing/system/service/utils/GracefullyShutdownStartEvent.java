package order.processing.system.service.utils;

import org.springframework.context.ApplicationEvent;

public class GracefullyShutdownStartEvent extends ApplicationEvent {

    public GracefullyShutdownStartEvent(Object source) {
        super(source);
    }
}
