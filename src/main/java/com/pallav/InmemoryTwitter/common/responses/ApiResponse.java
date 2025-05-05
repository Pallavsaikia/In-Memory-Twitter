package com.pallav.InmemoryTwitter.common.responses;
import com.pallav.InmemoryTwitter.config.AppCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {
    private boolean success;
    private T data; // Can be null or empty
    private String msg;
    private int code; // Numeric code (using AppCode enum)

    public ApiResponse(boolean success, T data, String msg, AppCode appCode) {
        this.success = success;
        this.data = data;
        this.msg = msg;
        this.code = appCode.getCode(); // Get the numeric value of AppCode
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
    public ResponseEntity<ApiResponse<T>> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }
}

