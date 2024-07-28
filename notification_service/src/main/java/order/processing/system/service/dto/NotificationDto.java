package order.processing.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import order.processing.system.service.entity.OrderStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificationDto {
    private String orderNumber;
    private long customerId;
    private List<Long> orderItemIds;
    private OrderStatus orderStatus;
}
