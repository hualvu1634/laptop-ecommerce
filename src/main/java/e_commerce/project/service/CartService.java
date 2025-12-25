package e_commerce.project.service;

import e_commerce.project.dto.request.CartRequest;
import e_commerce.project.dto.response.CartResponse;
import e_commerce.project.exception.ErrorCode;
import e_commerce.project.exception.NotFoundException;
import e_commerce.project.model.*;
import e_commerce.project.repository.*;
import lombok.RequiredArgsConstructor;

import java.util.List;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final  CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

        private Cart getCartByUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()-> new NotFoundException(ErrorCode.CART_NOT_FOUND));
       return cart;

        }

    public CartResponse addToCart(CartRequest request) {
        Cart cart = getCartByUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        int newQuantity = request.getQuantity();

        if (cartItem != null) {
            newQuantity += cartItem.getQuantity();
        } 
        
        if (newQuantity > product.getQuantity()) {
            throw new RuntimeException("Số lượng sản phẩm trong kho không đủ.");
        }

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(newQuantity);
        CartItem savedItem = cartItemRepository.save(cartItem);

        return mapToResponse(savedItem);
    }

    public List<CartResponse> getCart() {
        Cart cart = getCartByUser();

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        return cartItems.stream()
                .map(this::mapToResponse)
                .toList();
    }

 
    public List<CartResponse> updateCart( List<CartRequest> cartRequest) {
          Cart cart = getCartByUser();
        for (CartRequest request : cartRequest) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

            CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

            int newQuantity = request.getQuantity();

            
            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
                continue; // Chuyển sang sản phẩm tiếp theo
            } else if (newQuantity > product.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng trong kho (chỉ còn " + product.getQuantity() + ")");
            }

            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }

    
        return getCart();
    }

    public void removeProductFromCart( Long productId) {
          Cart cart = getCartByUser();

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_IN_CART));

        cartItemRepository.delete(cartItem);
    }


    private CartResponse mapToResponse(CartItem item) {
        return CartResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .build();
    }
}