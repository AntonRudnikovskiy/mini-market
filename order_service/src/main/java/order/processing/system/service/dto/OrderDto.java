package order.processing.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private long customerId;
    private List<Long> orderItemsIds;
}
