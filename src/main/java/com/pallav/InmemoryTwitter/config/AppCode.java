package com.pallav.InmemoryTwitter.config;

public enum AppCode {
    USER_REGISTERED(1001),
    USER_NOT_FOUND(1002),
    SUCCESS(1003),
    INTERNAL_ERROR(1004),
    NO_CONTENT(1005),
    UNAUTHORIZED(1006),
    FORBIDDEN(1007),
    VALIDATION_ERROR(1008),
    BAD_REQUEST(1009),
    INVALID_REQUEST(1010);

    private final int code;

    AppCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
