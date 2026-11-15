package com.prashobh.taskflow.exception;

import com.prashobh.taskflow.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                Instant.now(),
                404,
                "TASK_NOT_FOUND",
                ex.getMessage(),
                List.of(),
                request.getDescription(false).replace("uri=", "")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request){

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErrorResponse(
                Instant.now(),
                400,
                "BAD_REQUEST",
                "Validation failed",
                errors,
                request.getDescription(false).replace("uri=", "")));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request){
        return ResponseEntity.badRequest().body(new ErrorResponse(
                Instant.now(),
                400,
                "BAD_REQUEST",
                "Task ID must be a number",
                List.of(),
                request.getDescription(false).replace("uri=","")
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedRequests(HttpMessageNotReadableException ex, WebRequest request){
        return ResponseEntity.badRequest().body(new ErrorResponse(
                Instant.now(),
                400,
                "BAD_REQUEST",
                "Request body is malformed or contains invalid data",
                List.of(),
                request.getDescription(false).replace("uri=", "")
        ));
    }

    @ExceptionHandler(DuplicateTaskException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTaskException(DuplicateTaskException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                Instant.now(),
                409,
                "CONFLICT",
                ex.getMessage(),
                List.of(),
                request.getDescription(false).replace("uri=","")
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request){
        return ResponseEntity.internalServerError().body(new ErrorResponse(
                Instant.now(),
                500,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                List.of(),
                request.getDescription(false).replace("uri=","")
        ));
    }
}
