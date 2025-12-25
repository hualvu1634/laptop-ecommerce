package e_commerce.project.exception;


import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFound(NotFoundException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorDetail errorDetails = new ErrorDetail(
                LocalDateTime.now(),
                errorCode.getMessage(),
                String.valueOf(errorCode.getCode())
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
      
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorDetail errorDetails = new ErrorDetail(
                LocalDateTime.now(),
                errorMessage, 
                "VALIDATION_ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
// loi xac thuc
@ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleBadCredentials(BadCredentialsException ex) {
       ErrorCode errorCode = ErrorCode.AUTH_FAILED;
        ErrorDetail errorDetails = new ErrorDetail(
                LocalDateTime.now(),
                errorCode.getMessage(),
                String.valueOf(errorCode.getCode())
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED); // Trả về 401
    }
@ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDetail> handleRuntimeException(RuntimeException ex) {
        ErrorDetail errorDetails = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                "400" 
                
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


@ExceptionHandler(value = ExistedException.class)
public ResponseEntity<ErrorDetail> handleExistedException(ExistedException ex) {
        ErrorCode errorCode = ex.getErrorCode();
       
        ErrorDetail errorDetails = new ErrorDetail(
                LocalDateTime.now(),    
                errorCode.getMessage(),
                String.valueOf(errorCode.getCode()) 
        );

        return new ResponseEntity<>(errorDetails,HttpStatus.valueOf(errorCode.getCode()));
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDetail> handleGenericException(Exception ex, WebRequest request) {
        
        ErrorDetail errorDetails = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(), 
                "INTERNAL_SERVER_ERROR"
        );

    
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

