package com.securitygateway.loginandsignup.controller;

import com.securitygateway.loginandsignup.payload.requests.*;
import com.securitygateway.loginandsignup.payload.responses.GeneralAPIResponse;
import com.securitygateway.loginandsignup.payload.responses.RegisterResponse;
import com.securitygateway.loginandsignup.service.AuthenticationService;
import com.securitygateway.loginandsignup.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController{

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Register request received for email: {}", registerRequest.getEmail());
        return authenticationService.registerUser(registerRequest);
    }



    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("login request received for email {}", loginRequest.getEmail());
        return authenticationService.loginUser(loginRequest);
    }

}
