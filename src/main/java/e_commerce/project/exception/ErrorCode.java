package e_commerce.project.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "Lỗi hệ thống"),
    //409
    USER_EXISTED(409, "Tài khoản đã tồn tại"),
    PRODUCT_EXISTED(409, "Sản phẩm đã tồn tại"),
    CATEGORY_EXISTED(409, "Danh mục đã tồn tại"),

    //404 NOT FOUND
    USER_NOT_FOUND(404, "Không tìm thấy người dùng"),
    PRODUCT_NOT_FOUND(404, "Sản phẩm không tồn tại"),
    CATEGORY_NOT_FOUND(404, "Danh mục không tồn tại"),
    CART_NOT_FOUND(404, "Giỏ hàng không tồn tại"),
    PRODUCT_NOT_IN_CART(404, "Sản phẩm không có trong giỏ hàng"),


    //401 AUTH
    AUTH_FAILED(401, "Username hoặc Password không chính xác");


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}