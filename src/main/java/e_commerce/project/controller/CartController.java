package e_commerce.project.controller;
import e_commerce.project.dto.request.CartRequest;
import e_commerce.project.dto.response.CartResponse;
import e_commerce.project.service.CartService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    //thêm sản phẩm vào giỏ hàng
    @PostMapping
    public ResponseEntity<CartResponse> addToCart( @Valid @RequestBody CartRequest request) {
        return new ResponseEntity<>(cartService.addToCart(request),HttpStatus.CREATED);
    }
    //xem giỏ hàng
    @GetMapping
    public ResponseEntity<?> getCart(){
        return ResponseEntity.ok(cartService.getCart());
    }
    //sửa sản phẩm trong giỏ hàng
    @PutMapping
    public ResponseEntity<?> updateCart( @Valid @RequestBody List<CartRequest> cartRequest){
        return ResponseEntity.ok(cartService.updateCart(cartRequest));
    }
    //xóa sản phẩm trong giỏ hàng
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long productId) {
        
            cartService.removeProductFromCart( productId);
            return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ hàng thành công.");
        
    }
    
    
}