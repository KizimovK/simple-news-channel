package com.example.simplenewschannel.web.handler;

import com.example.simplenewschannel.dto.response.ExceptionResponse;
import com.example.simplenewschannel.exception.*;

import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerHandler {

    @ExceptionHandler(RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse refreshTokenException(RefreshTokenException exception, WebRequest request) {
        return buildExceptionResponse(exception,request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse alreadyExistsException(AlreadyExistsException exception, WebRequest request) {
        return buildExceptionResponse(exception, request);
    }


    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse existsException(EntityExistsException exception, WebRequest request){
        return buildExceptionResponse(exception, request);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(EntityNotFoundException exception, WebRequest request){
        return buildExceptionResponse(exception, request);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notValidException(MethodArgumentNotValidException exception, WebRequest request){
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errorMessages = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return buildExceptionResponse(exception, request);
    }
    @ExceptionHandler(AccessiblyCheckException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse notAccessiblyCheckedException(AccessiblyCheckException exception, WebRequest request){
        return buildExceptionResponse(exception, request);
    }

    private ExceptionResponse buildExceptionResponse(Exception exception, WebRequest request) {
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .build();
    }

}
