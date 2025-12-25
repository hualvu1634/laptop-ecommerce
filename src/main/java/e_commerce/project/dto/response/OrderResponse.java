package e_commerce.project.dto.response;

import java.time.LocalDateTime;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder

public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String customerName; // Tên người mua
    private String address;      // Lấy từ request
    private String phone;  // Lấy từ request
    private LocalDateTime orderDate;
    private Long totalMoney;   // Tổng tiền đơn hàng
    private List<OrderItem> items;
}
