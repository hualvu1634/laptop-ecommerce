package e_commerce.project.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce.project.model.Product;
import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product>  findByCategoryId(Long categoryId);
    boolean existsByName(String name);
    Optional<Product> findByName(String productName);
    
}
