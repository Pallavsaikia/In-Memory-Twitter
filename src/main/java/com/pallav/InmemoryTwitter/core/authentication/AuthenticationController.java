package com.pallav.InmemoryTwitter.core.authentication;

import com.pallav.InmemoryTwitter.common.responses.ApiResponse;
import com.pallav.InmemoryTwitter.common.responses.SuccessResponse;
import com.pallav.InmemoryTwitter.core.user.User;
import com.pallav.InmemoryTwitter.core.user.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {



    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody UserRegisterDTO user) {
        // Call the service to register the user
        User savedUser = authenticationService.register(user.getUsername(),user.getEmail(),user.getPassword());

        // Success Response with the saved user details
        SuccessResponse<User> successResponse = new SuccessResponse<>(savedUser, "User registered successfully.", null);

        return successResponse.toResponseEntity(HttpStatus.CREATED);
    }
};