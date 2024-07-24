package order.processing.system.service.mapper;

import order.processing.system.service.dto.OrderDto;
import order.processing.system.service.entity.Order;
import order.processing.system.service.entity.OrderItem;
import order.processing.system.service.repository.OrderItemRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Mapping(source = "orderItems", target = "orderItemsIds", qualifiedByName = "mapOrderDtoToOrderItemsIds")
    public abstract OrderDto toDto(Order order);

    @Mapping(source = "orderItemsIds", target = "orderItems", qualifiedByName = "mapOrderIdsToOrders")
    public abstract Order toEntity(OrderDto order);

    @Named("mapOrderDtoToOrderItemsIds")
    protected List<Long> mapOrderDtoToOrderItemsIds(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return Collections.emptyList();
        }
        List<Long> list = new ArrayList<>();
        orderItems.forEach(orderItem -> list.add(orderItem.getId()));
        return list;
    }

    @Named("mapOrderIdsToOrders")
    protected List<OrderItem> mapOrderIdsToOrders(List<Long> orderItemsIds) {
        if (orderItemsIds == null) {
            return Collections.emptyList();
        }
        List<OrderItem> list = new ArrayList<>();
        orderItemsIds.forEach(o -> list.add(orderItemRepository.findById(o).orElseThrow()));
        return list;
    }

    public List<OrderDto> mapToUserDto(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .toList();
    }
}
