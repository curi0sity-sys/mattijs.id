package com.pascal.compiler.api;

import com.pascal.compiler.api.dto.ErrorResponse;
import com.pascal.compiler.service.CompilationException;
import com.pascal.compiler.service.PascalFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler for REST API
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PascalFileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFound(
            PascalFileNotFoundException ex, WebRequest request) {
        log.error("File not found: {}", ex.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .error("File Not Found")
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
            
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CompilationException.class)
    public ResponseEntity<ErrorResponse> handleCompilationError(
            CompilationException ex, WebRequest request) {
        log.error("Compilation error: {}", ex.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .error("Compilation Error")
            .message(ex.getMessage())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timestamp(LocalDateTime.now())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
            
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(
            Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ErrorResponse error = ErrorResponse.builder()
            .error("Internal Server Error")
            .message("An unexpected error occurred")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timestamp(LocalDateTime.now())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
            
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

