package com.urbankicks.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.urbankicks.models.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collecting the first validation error for each field
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,       // Key is the field name
                        FieldError::getDefaultMessage,  // Value is the error message
                        (existing, replacement) -> existing // Keep the first encountered error
                ));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Validation failed", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_BODY", "Request body cannot be empty.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", "Invalid Argument!");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<APIResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "METHOD_NOT_SUPPORTED", "Invalid Argument!");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "ILLEGAL_ARGUMENT", ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<APIResponse> handleIoException(IOException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "IO_EXCEPTION", ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "BAD_CREDENTIALS", ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<APIResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "BAD_CREDENTIALS", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "ACCESS_DENIED", ex.getMessage());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<APIResponse> handleSignatureException(SignatureException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "INVALID_SIGNATURE", ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "TOKEN_EXPIRED", ex.getMessage());
    }

    @ExceptionHandler(DecodingException.class)
    public ResponseEntity<APIResponse> handleDecodingException(DecodingException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "TOKEN_DECODING_ERROR", ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<APIResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "MAX_UPLOAD_SIZE_EXCEEDED", "Maximum upload size is 50MB.");
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<APIResponse> handleJsonParseExceptionException(JsonParseException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Token", ex.getMessage());
    }

    @ExceptionHandler(DeserializationException.class)
    public ResponseEntity<APIResponse> handleDeserializationExceptionException(DeserializationException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Token", ex.getMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<APIResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Custom Validation Error");
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<APIResponse> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        String fieldName = ex.getRequestPartName();

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", ex.getMessage(), Map.of(fieldName, "101"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleException(Exception ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "SERVER_ERROR", ex.getMessage());
    }

    // Utility method to build a common error response
    private ResponseEntity<APIResponse> buildErrorResponse(HttpStatus status, String respCode, String respMsg) {
        return buildErrorResponse(status, respCode, respMsg, null);
    }

    // Overloaded method for including errors
    private ResponseEntity<APIResponse> buildErrorResponse(HttpStatus status, String respCode, String respMsg, Map<String, String> errors) {
        APIResponse response = APIResponse.builder()
                .status(status.value())
                .respCode(respCode)
                .respMsg(respMsg)
                .errors(errors)
                .build();

        return new ResponseEntity<>(response, status);
    }
}
