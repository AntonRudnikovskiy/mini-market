package order.processing.system.service.service;

public interface NotificationSender<T> {
    void send(T t);
}
