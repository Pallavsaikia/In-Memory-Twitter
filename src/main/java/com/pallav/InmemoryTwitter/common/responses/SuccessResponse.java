package com.pallav.InmemoryTwitter.common.responses;

import com.pallav.InmemoryTwitter.config.AppCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponse<T> extends ApiResponse<T> {

    // Default success message and code
    private static final String DEFAULT_SUCCESS_MSG = "Operation successful";
    private static final AppCode DEFAULT_SUCCESS_CODE = AppCode.SUCCESS;
    private static final HttpStatus DEFAULT_SUCCESS_STATUS = HttpStatus.OK;

    public SuccessResponse(T data, String msg, AppCode appCode) {
        // If msg or appCode is null, default values will be used
        super(true, data, (msg != null) ? msg : DEFAULT_SUCCESS_MSG, (appCode != null) ? appCode : DEFAULT_SUCCESS_CODE);
    }

    // Convert SuccessResponse to ResponseEntity with optional HTTP status
    public ResponseEntity<ApiResponse<T>> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, (status != null) ? status : DEFAULT_SUCCESS_STATUS);
    }

    // Convert SuccessResponse to ResponseEntity with default HTTP status
    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return new ResponseEntity<>(this, DEFAULT_SUCCESS_STATUS);
    }
}
