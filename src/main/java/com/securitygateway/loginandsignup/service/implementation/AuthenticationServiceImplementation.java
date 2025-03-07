package com.securitygateway.loginandsignup.service.implementation;

import com.securitygateway.loginandsignup.exceptions.custom.ResourceNotFoundException;
import com.securitygateway.loginandsignup.model.User;
import com.securitygateway.loginandsignup.model.Username;
import com.securitygateway.loginandsignup.payload.requests.*;
import com.securitygateway.loginandsignup.payload.responses.GeneralAPIResponse;
import com.securitygateway.loginandsignup.payload.responses.RegisterResponse;
import com.securitygateway.loginandsignup.payload.responses.RegisterVerifyResponse;
import com.securitygateway.loginandsignup.payload.responses.UserProfile;
import com.securitygateway.loginandsignup.repository.UserRepository;
import com.securitygateway.loginandsignup.service.AuthenticationService;
import com.securitygateway.loginandsignup.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImplementation implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest) {
        try {
            log.info("Received request to register user with email {}", registerRequest.getEmail());
            Optional<User> existingUserOpt = userRepository.findByEmail(registerRequest.getEmail().trim().toLowerCase());
            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();
                log.info("User already exists with email {}", registerRequest.getEmail());
                return new ResponseEntity<>(RegisterResponse.builder()
                        .message("User already exists")
                        .build(), HttpStatus.BAD_REQUEST);
            } else {
                log.info("User does not exist with email {}, so this user will be created", registerRequest.getEmail());
                User newUser = createUser(registerRequest);
                userRepository.save(newUser);
                log.info("User saved with the email {}", registerRequest.getEmail());
                return new ResponseEntity<>(RegisterResponse.builder()
                        .message("User Registered successfully!")
                        .build(), HttpStatus.CREATED);
            }
        }
        catch (Exception e) {
            log.error("Failed to register user with email {}", registerRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RegisterResponse.builder()
                    .message("Failed to register user. Please try again later.")
                    .build());
        }
    }


    private User createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(new Username(registerRequest.getFirstName().trim(), registerRequest.getLastName().trim()));
        user.setEmail(registerRequest.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        return user;
    }


    // -----------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail().trim().toLowerCase();
        String password = loginRequest.getPassword();
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                        ResourceNotFoundException::new
                );
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            RegisterVerifyResponse jwtToken = jwtService.generateJwtToken(user);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            log.info("user whose email is {} not found in Database", email);
            return new ResponseEntity<>(GeneralAPIResponse.builder().message("User with this email does not exist").build(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("Failed to authenticate user with email {}", email, e);
            return new ResponseEntity<>(GeneralAPIResponse.builder().message("Invalid credentials").build(), HttpStatus.BAD_REQUEST);
        }


    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<?> myProfile(ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail().trim().toLowerCase();
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    ResourceNotFoundException::new
            );
            return new ResponseEntity<>(UserProfile.builder()
                    .id(user.getId())
                    .firstName(user.getName().getFirstName())
                    .lastName(user.getName().getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole())
                    .build(), HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            log.info("user with email {} not found in the Database", email);
            return new ResponseEntity<>(GeneralAPIResponse.builder().message("user does not exist with this email").build(), HttpStatus.NOT_FOUND);
        }
    }
}
