package e_commerce.project.exception;


import java.time.LocalDateTime;

public record ErrorDetail(LocalDateTime timestamp,String message,String errorcode) {

}

