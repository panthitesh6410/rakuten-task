package com.securitygateway.loginandsignup.service;


import com.securitygateway.loginandsignup.payload.requests.*;
import com.securitygateway.loginandsignup.payload.responses.RegisterResponse;
import org.springframework.http.ResponseEntity;




public interface AuthenticationService {
      ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest);
      ResponseEntity<?> loginUser(LoginRequest loginRequest);
      ResponseEntity<?> myProfile(ForgotPasswordRequest forgotPasswordRequest);


}
