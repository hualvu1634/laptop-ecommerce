package e_commerce.project.service;


import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import e_commerce.project.dto.request.UserRequest;
import e_commerce.project.dto.response.UserResponse;
import e_commerce.project.exception.ErrorCode;
import e_commerce.project.exception.ExistedException;
import e_commerce.project.exception.NotFoundException;
import e_commerce.project.model.Cart;
import e_commerce.project.model.User;
import e_commerce.project.repository.CartRepository;
import e_commerce.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class UserService {

private final UserRepository userRepository;
private final CartRepository cartRepository;
private  final PasswordEncoder passwordEncoder;



public UserResponse addAccount(UserRequest accountRequest){
    if(userRepository.existsByUsername(accountRequest.getUsername()))
        throw new ExistedException(ErrorCode.USER_EXISTED);
    User accounts = User.builder()
                        .username(accountRequest.getUsername())
                        .password(passwordEncoder.encode(accountRequest.getPassword()))
                        .name(accountRequest.getName())
                        .phone(accountRequest.getPhone())
                        .role("USER")
                        .build();
    User saveAccounts = userRepository.save(accounts);
    
    Cart cart = Cart.builder().user(saveAccounts).build();
    Cart savedCart = cartRepository.save(cart);
    UserResponse accountResponse = UserResponse.builder().
                                        id(saveAccounts.getId())
                                        .name(saveAccounts.getName())
                                        .phone(saveAccounts.getPhone())
                                        .role(saveAccounts.getRole())
                                        .build();
    return accountResponse;                                    
                                      
}

public List<UserResponse> getAllAccounts(){
    List<User> accountEntities = userRepository.findAll();
    return accountEntities.stream()
            .map(account -> UserResponse.builder()
                    .id(account.getId())
                    .name(account.getName())
                    .phone(account.getPhone())
                    .role(account.getRole())
                    .build())
            .toList();
}

     public UserResponse getAccount() {
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .role(user.getRole())
                
                .build();
    }

    public UserResponse getAccountById(Long id) {
        
        User account = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
      
        return UserResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .phone(account.getPhone())
                .role(account.getRole())
              
                .build();
    }


    public String deleteAccount(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
        return "Xóa tài khoản thành công";
    }




}

