package order.processing.system.service.repository;

import order.processing.system.service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(nativeQuery = true, value = """
            SELECT EXISTS(SELECT 1 FROM customer WHERE id = :customerId)
            """)
    boolean existsByCustomerId(long customerId);
}
