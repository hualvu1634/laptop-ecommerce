package e_commerce.project.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
  @NotBlank(message = "Tên người nhận không được để trống")
    private String customerName;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String address;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại không hợp lệ") // Regex kiểm tra 10 số
    private String phone;
    
}