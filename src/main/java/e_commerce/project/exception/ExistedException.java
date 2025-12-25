package e_commerce.project.exception;

import lombok.Getter;

@Getter
public class ExistedException extends RuntimeException{
    private ErrorCode errorCode;
    public ExistedException(ErrorCode errorCode){
        super(errorCode.getMessage()); 
        this.errorCode = errorCode;   
    }
}
