package e_commerce.project.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
 
    private Long productId; //id san pham
    private String productName; //ten san pham
    private Long price;       // Giá 
    private Integer quantity;   //so luong 
    
}