package e_commerce.project.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private ErrorCode errorCode;
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage()); 
        this.errorCode = errorCode;    
    }
}