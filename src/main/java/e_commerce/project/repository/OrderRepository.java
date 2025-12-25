package e_commerce.project.repository;
import e_commerce.project.model.Order;



import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
 
}