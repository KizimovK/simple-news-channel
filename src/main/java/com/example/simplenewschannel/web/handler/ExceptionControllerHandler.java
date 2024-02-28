package com.example.simplenewschannel.web.handler;

import com.example.simplenewschannel.dto.response.ExceptionResponse;
import com.example.simplenewschannel.exception.EntityExistsException;
import com.example.simplenewschannel.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerHandler {
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> existsException(EntityExistsException exception){
        log.error("Error create or update entity ", exception);
        return ResponseEntity.badRequest().body(new ExceptionResponse(exception.getMessage()));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(EntityNotFoundException exception){
        log.error("Error not found entity ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(exception.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> notValidException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errorMessages = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.badRequest().body(new ExceptionResponse(errorMessages.toString()));
    }
    //Todo: make AccessiblyCheckException handler
}
