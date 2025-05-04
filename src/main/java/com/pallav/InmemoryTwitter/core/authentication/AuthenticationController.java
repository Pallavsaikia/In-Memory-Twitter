package com.pallav.InmemoryTwitter.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;



    @GetMapping("/login")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok("Adada");
    }
}