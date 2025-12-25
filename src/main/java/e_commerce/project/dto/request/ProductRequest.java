package e_commerce.project.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống.")
    @Size(max = 100, message = "Tên sản phẩm không được quá 100 ký tự.")
    private String name;

    @Size(max = 250, message = "Mô tả không được quá 250 ký tự.")
    private String description;
    
    @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0.")
    private Long price;
 
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0.")
    private Integer quantity ;//mac dinh neu k go
    
    @NotNull(message = "ID danh mục không được để trống.")
    private Long categoryId;
    
}