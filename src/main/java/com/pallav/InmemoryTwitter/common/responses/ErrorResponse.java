package com.pallav.InmemoryTwitter.common.responses;
import com.pallav.InmemoryTwitter.config.AppCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse extends ApiResponse<Object> {

    // Default error message and code
    private static final String DEFAULT_ERROR_MSG = "An error occurred";
    private static final AppCode DEFAULT_ERROR_CODE = AppCode.INTERNAL_ERROR;
    private static final HttpStatus DEFAULT_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public ErrorResponse(String msg, AppCode appCode) {
        super(false, null, (msg != null) ? msg : DEFAULT_ERROR_MSG, (appCode != null) ? appCode : DEFAULT_ERROR_CODE);
    }

    public ErrorResponse(String msg, AppCode appCode, Object data) {
        super(false, data, (msg != null) ? msg : DEFAULT_ERROR_MSG, (appCode != null) ? appCode : DEFAULT_ERROR_CODE);
    }

    // Convert ErrorResponse to ResponseEntity with optional HTTP status
    public ResponseEntity<ApiResponse<Object>> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, (status != null) ? status : DEFAULT_ERROR_STATUS);
    }

    // Convert ErrorResponse to ResponseEntity with default HTTP status
    public ResponseEntity<ApiResponse<Object>> toResponseEntity() {
        return new ResponseEntity<>(this, DEFAULT_ERROR_STATUS);
    }
}
