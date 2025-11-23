package com.controlgastos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la API
 * Captura y formatea todas las excepciones de forma consistente
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Maneja excepciones de recurso no encontrado
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Maneja excepciones de recurso duplicado
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
            DuplicateResourceException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflicto de datos",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    
    /**
     * Maneja excepciones de autenticación fallida (HU002)
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciales incorrectas",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Maneja errores de validación de campos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                errors,
                request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Maneja excepciones generales no capturadas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Clase interna para formatear respuestas de error
     */
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        
        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }
        
        // Getters
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
    }
    
    /**
     * Clase interna para formatear errores de validación
     */
    public static class ValidationErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private Map<String, String> validationErrors;
        private String path;
        
        public ValidationErrorResponse(LocalDateTime timestamp, int status, String error, 
                                      Map<String, String> validationErrors, String path) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.validationErrors = validationErrors;
            this.path = path;
        }
        
        // Getters
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public Map<String, String> getValidationErrors() { return validationErrors; }
        public String getPath() { return path; }
    }
}
