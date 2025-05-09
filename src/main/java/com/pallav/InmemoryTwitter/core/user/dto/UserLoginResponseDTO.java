package com.pallav.InmemoryTwitter.core.user.dto;

public record UserLoginResponseDTO(String id,
                                   String username,
                                   String email,
                                   String accessToken,
                                   String[] roles,
                                   String[] privilege) {
}
