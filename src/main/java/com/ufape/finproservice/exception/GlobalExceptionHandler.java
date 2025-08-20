package com.ufape.finproservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ApiErrorResponse error = new ApiErrorResponse(
                "ACCESS_DENIED",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> customException(CustomException ex) {
        ApiErrorResponse error = new ApiErrorResponse(
                ex.exceptionMessage.name(),
                ex.exceptionMessage.getDescription()
        );
        return ResponseEntity.status(ex.exceptionMessage.getCode()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericRuntime(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse(
                "NOT_HANDLER_ERROR",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
