package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.auth.*;
import com.example.simplenewschannel.dto.response.SimpleResponse;
import com.example.simplenewschannel.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {

    private final SecurityService securityService;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse authUser(@RequestBody LoginRequest loginRequest){
        return securityService.authenticationUser(loginRequest);
    }


    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return securityService.refreshToken(request);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse logoutUser(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
        return new SimpleResponse("User logout. Username is: " + userDetails.getUsername());
    }


}
