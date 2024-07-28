package order.processing.system.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.dto.NotificationDto;
import order.processing.system.service.dto.OrderDto;
import order.processing.system.service.entity.Customer;
import order.processing.system.service.entity.Order;
import order.processing.system.service.entity.OrderStatus;
import order.processing.system.service.exceptions.InvalidOrderException;
import order.processing.system.service.exceptions.NoSuchOrderException;
import order.processing.system.service.mapper.NotificationMapper;
import order.processing.system.service.mapper.OrderMapper;
import order.processing.system.service.producer.NotificationProducer;
import order.processing.system.service.repository.CustomerRepository;
import order.processing.system.service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;
    private final NotificationProducer notificationProducer;
    private final NotificationMapper notificationMapper;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        try {
            Optional<Customer> customerById = customerRepository.findById(orderDto.getCustomerId());
            if (customerById.isPresent()) {
                Order order = orderMapper.toEntity(orderDto);
                order.setOrderStatus(OrderStatus.PROCESSING);
                orderRepository.save(order);

                NotificationDto notificationDto = notificationMapper.toDto(order);
                notificationProducer.send(notificationDto);

                log.info("Order was successfully saved and sent: {}", order);
                return orderMapper.toDto(order);
            } else {
                throw new InvalidOrderException("Customer does not exist.");
            }
        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage());
            throw e;
        }
    }

    public OrderDto findOrderById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("Order does not exist"));
        log.info("Order was successfully retrieved: {}", order);
        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrderByCustomerId(long id) {
        List<Order> allByCustomerId = orderRepository.findAllByCustomerId(id);
        return orderMapper.mapToUserDto(allByCustomerId);
    }

    @Transactional
    public void deleteOrderById(long orderId) {
        Order orderById = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchOrderException("Order does not exist"));
        orderById.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.deleteById(orderId);
    }
}
