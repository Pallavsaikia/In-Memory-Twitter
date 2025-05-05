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

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> authenticate(@Valid @RequestBody UserRegisterDTO user) {
//        User savedUser = authenticationService.registerUser(user);

        // Success Response with default message and code
        SuccessResponse<User> successResponse = new SuccessResponse<>(null, null, null);
        return successResponse.toResponseEntity(HttpStatus.CREATED);
    }
}