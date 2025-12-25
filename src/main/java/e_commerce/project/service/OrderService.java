package e_commerce.project.service;

import e_commerce.project.dto.request.OrderRequest;
import e_commerce.project.dto.response.OrderItem;
import e_commerce.project.dto.response.OrderResponse;
import e_commerce.project.exception.ErrorCode;
import e_commerce.project.exception.ExistedException;
import e_commerce.project.exception.NotFoundException;
import e_commerce.project.model.*;
import e_commerce.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

     private final CartRepository cartRepository;
     private final CartItemRepository cartItemRepository;
     private final OrderRepository orderRepository;
     private final OrderDetailRepository orderDetailRepository;
     private  final ProductRepository productRepository;
     private final UserRepository userRepository;

    @Transactional 
    public OrderResponse placeOrder(OrderRequest request) {
       
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()-> new NotFoundException(ErrorCode.USER_NOT_FOUND));
       

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        
        Order order = new Order();
        order.setUser(user);
        order.setAddress(request.getAddress());       
        order.setPhoneNumber(request.getPhone()); 
        order.setOrderDate(LocalDateTime.now());
        
     
        order = orderRepository.save(order);

        Long totalMoney = 0L;
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<OrderItem> itemDtos = new ArrayList<>();
        List<Product> productsToUpdate = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = item.getProduct();

        
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng.");
            }

            // 3.2 Trừ kho
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productsToUpdate.add(product);


            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(product.getPrice());
            orderDetails.add(detail);

            OrderItem itemDto = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .subTotal(product.getPrice() * item.getQuantity())
                    .build();
            itemDtos.add(itemDto);

            // Cộng tổng tiền
            totalMoney += (product.getPrice() * item.getQuantity());
        }

        productRepository.saveAll(productsToUpdate);
        orderDetailRepository.saveAll(orderDetails);
        order.setTotalMoney(totalMoney);
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);

        cartItemRepository.deleteAllByCartId(cart.getId());


        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(user.getId())
                .customerName(request.getCustomerName()) 
                .address(order.getAddress())
                .phone(order.getPhoneNumber())
                .orderDate(order.getOrderDate())
                .totalMoney(order.getTotalMoney())
                .items(itemDtos)
                .build();
    }

}