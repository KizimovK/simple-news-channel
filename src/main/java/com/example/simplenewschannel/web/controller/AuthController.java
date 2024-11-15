package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.auth.*;
import com.example.simplenewschannel.repository.UserRepository;
import com.example.simplenewschannel.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {

    private final SecurityService securityService;
    private final UserRepository userRepository;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse authUser(@RequestBody LoginRequest loginRequest){
        return securityService.authenticationUser(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleResponse registerNewUser(@RequestBody CreateUserRequest request) {
        securityService.register(request);
        return new SimpleResponse("Created new user!");
    }

    @PostMapping("refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return securityService.refreshToken(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse logoutUser(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
        return new SimpleResponse("User logout. Username is: " + userDetails.getUsername());
    }


}
