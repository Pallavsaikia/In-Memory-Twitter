package com.pallav.InmemoryTwitter.core.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
