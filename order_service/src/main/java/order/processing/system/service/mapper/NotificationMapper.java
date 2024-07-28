package order.processing.system.service.mapper;

import order.processing.system.service.dto.NotificationDto;
import order.processing.system.service.entity.Order;
import order.processing.system.service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class NotificationMapper {
    @Mapping(source = "orderItems", target = "orderItemIds", qualifiedByName = "mapOrderItemsToOrderItemsId")
    public abstract NotificationDto toDto(Order order);

    @Named("mapOrderItemsToOrderItemsId")
    protected List<Long> mapOrderItemsToOrderItemsId(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return Collections.emptyList();
        }
        List<Long> listItemIds = new ArrayList<>();
        orderItems.forEach(orderItem -> listItemIds.add(orderItem.getId()));
        return listItemIds;
    }
}
