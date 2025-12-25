package e_commerce.project.dto.request;



import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;  //id san pham
    @Min(value = 1,message = "Số lượng phải lớn hơn 0")
    @NotNull(message = "Số lượng không được để trống")
    private Integer quantity; //so luong
    
}