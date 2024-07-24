package order.processing.system.service.repository;

import order.processing.system.service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = """
            SELECT EXISTS(SELECT 1 FROM order WHERE id = :orderId)
            """)
    boolean existsByOrderId(long orderId);

    @Query(nativeQuery = true, value = """
            SELECT * FROM Order
            WHERE customer_id = :customerId
            """)
    List<Order> findAllByCustomerId(long customerId);
}
