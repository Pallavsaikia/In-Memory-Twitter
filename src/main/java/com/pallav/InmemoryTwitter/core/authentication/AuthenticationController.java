package com.pallav.InmemoryTwitter.core.authentication;

import com.pallav.InmemoryTwitter.common.responses.ApiResponse;
import com.pallav.InmemoryTwitter.common.responses.SuccessResponse;
import com.pallav.InmemoryTwitter.core.user.User;
import com.pallav.InmemoryTwitter.core.user.dto.UserLoginDTO;
import com.pallav.InmemoryTwitter.core.user.dto.UserLoginResponseDTO;
import com.pallav.InmemoryTwitter.core.user.dto.UserRegisterDTO;
import com.pallav.InmemoryTwitter.core.user.dto.UserRegisterResponseDTO;
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
    public ResponseEntity<ApiResponse<UserRegisterResponseDTO>> registerUser(@Valid @RequestBody UserRegisterDTO user) {
        // Call the service to register the user
        User savedUser = authenticationService.register(user.getUsername(),user.getEmail(),user.getPassword());

        // Success Response with the saved user details

        UserRegisterResponseDTO userResponseDTO=new UserRegisterResponseDTO(savedUser.id(),savedUser.username(),savedUser.email());
        SuccessResponse<UserRegisterResponseDTO> successResponse = new SuccessResponse<>(userResponseDTO, "User registered successfully.", null);
        return successResponse.toResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDTO>> registerUser(@Valid @RequestBody UserLoginDTO user) {
        // Call the service to register the user
        User loggedInUser = authenticationService.login(user.getUsername(),user.getPassword());

        // Success Response with the saved user details
        String accessToken="Adada";
        String [] roles=new String[]{};
        String [] privilege=new String[]{};
        UserLoginResponseDTO userResponseDTO=new UserLoginResponseDTO(loggedInUser.id(),
                loggedInUser.username(),
                loggedInUser.email(),
                accessToken,
                roles,
                privilege

        );
        SuccessResponse<UserLoginResponseDTO> successResponse = new SuccessResponse<>(userResponseDTO, "User registered successfully.", null);
        return successResponse.toResponseEntity(HttpStatus.CREATED);
    }
};