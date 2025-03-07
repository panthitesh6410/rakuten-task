package com.securitygateway.loginandsignup.controller;

import com.securitygateway.loginandsignup.payload.requests.ForgotPasswordRequest;
import com.securitygateway.loginandsignup.payload.responses.GeneralAPIResponse;
import com.securitygateway.loginandsignup.payload.responses.UserProfile;
import com.securitygateway.loginandsignup.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final AuthenticationService authenticationService;

    @PostMapping("/myProfile")
    public ResponseEntity<?> myProfile(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest){
        log.info("My profile request received for email {}", forgotPasswordRequest.getEmail());
        return authenticationService.myProfile(forgotPasswordRequest);
    }

}
