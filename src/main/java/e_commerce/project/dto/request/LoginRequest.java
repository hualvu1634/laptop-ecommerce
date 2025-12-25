package e_commerce.project.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
     @NotBlank(message = "Username không được để trống.")
    @Size(min = 8, max = 25, message = "Username phải có từ 8 đến 25 ký tự.")
    private String username;

    @NotBlank(message = "Password không được để trống.")
    @Size(min = 8, max = 25, message = "Password phải có từ 8 đến 25 ký tự.")
    private String password;
}