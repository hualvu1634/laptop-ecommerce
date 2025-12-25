package e_commerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import e_commerce.project.dto.request.UserRequest;
import e_commerce.project.dto.response.UserResponse;
import e_commerce.project.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> addUser( @Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<UserResponse>(userService.addAccount(userRequest),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<UserResponse> getUSer( ){
        return ResponseEntity.ok(userService.getAccount());
    }
     @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUSer( @Valid @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getAccountById(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> accounts = userService.getAllAccounts();
        return ResponseEntity.ok(accounts);
       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(  @Valid @PathVariable("id") Long id){
        return new ResponseEntity<>(userService.deleteAccount(id),HttpStatus.ACCEPTED);   
    }
}
