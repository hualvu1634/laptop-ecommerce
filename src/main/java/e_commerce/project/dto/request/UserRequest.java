package e_commerce.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Username không được để trống.")
    @Size(min = 8, max = 25, message = "Username phải có từ 8 đến 25 ký tự.")
    private String username;

    @NotBlank(message = "Password không được để trống.")
    @Size(min = 8, max = 25, message = "Password phải có từ 8 đến 25 ký tự.")
    private String password;

    @NotBlank(message = "Tên không được để trống.")
    private String name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại không hợp lệ") 
    private String phone;
}