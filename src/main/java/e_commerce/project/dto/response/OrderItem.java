package e_commerce.project.dto.response;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class OrderItem {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Long price;       
    private Long subTotal;    
}