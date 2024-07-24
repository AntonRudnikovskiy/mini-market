package order.processing.system.service.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.service.dto.OrderDto;
import order.processing.system.service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
@Slf4j
public class OrderController {
    private final OrderService productService;

    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @NotNull OrderDto orderDto) {
        OrderDto order = productService.createOrder(orderDto);
        log.info("Order successfully created: {}", order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable long orderId) {
        OrderDto orderById = productService.findOrderById(orderId);
        log.info("Order successfully retrieved: {}", orderId);
        return ResponseEntity.ok(orderById);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<OrderDto>> getAllOrderByCustomerId(@PathVariable long customerId) {
        List<OrderDto> allOrderByCustomerId = productService.getAllOrderByCustomerId(customerId);
        log.info("Orders successfully retrieved: {}", allOrderByCustomerId);
        return ResponseEntity.ok(allOrderByCustomerId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrderById(@PathVariable long orderId) {
        productService.deleteOrderById(orderId);
        log.info("Order successfully removed: {}", orderId);
        return ResponseEntity.ok().build();
    }
}
