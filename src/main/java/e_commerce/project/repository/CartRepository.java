package e_commerce.project.repository;

import e_commerce.project.model.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
 
    Optional<Cart> findByUserId(Long userId);
}