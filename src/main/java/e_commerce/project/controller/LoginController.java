package e_commerce.project.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.project.dto.request.LoginRequest;
import e_commerce.project.dto.response.LoginResponse;
import e_commerce.project.service.AuthenticationService;
import jakarta.validation.Valid;


@RestController
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService; 
        @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        return authenticationService.login(loginRequest);
    }
}

