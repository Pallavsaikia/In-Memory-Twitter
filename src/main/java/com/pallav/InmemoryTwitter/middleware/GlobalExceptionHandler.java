package com.pallav.InmemoryTwitter.middleware;

import com.pallav.InmemoryTwitter.common.responses.ApiResponse;
import com.pallav.InmemoryTwitter.common.responses.ErrorResponse;
import com.pallav.InmemoryTwitter.config.AppCode;
import com.pallav.InmemoryTwitter.exceptions.AuthenticationException;
import com.pallav.InmemoryTwitter.exceptions.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // Or @ControllerAdvice for MVC
public class GlobalExceptionHandler {


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        // Provide a user-friendly error message when the body is missing
        String errorMessage = "Request body is missing or malformed. Please provide a valid body.";

        // Wrap the error message in an ApiResponse and return it
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, null, errorMessage, AppCode.INVALID_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), AppCode.BAD_REQUEST);
        return errorResponse.toResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMsg.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }

        ErrorResponse errorResponse = new ErrorResponse(
                errorMsg.toString().trim(),
                AppCode.VALIDATION_ERROR
        );

        return errorResponse.toResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // For debugging/logging
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong", AppCode.INTERNAL_ERROR);
        return errorResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), AppCode.UNAUTHORIZED);
        return errorResponse.toResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    // Handle ForbiddenException (403 Forbidden)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<Object>> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), AppCode.FORBIDDEN);
        return errorResponse.toResponseEntity(HttpStatus.FORBIDDEN);
    }
}