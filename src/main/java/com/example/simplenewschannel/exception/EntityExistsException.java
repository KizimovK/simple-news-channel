package com.example.simplenewschannel.exception;

public class EntityExistsException extends RuntimeException{
    public EntityExistsException(String message) {
        super(message);
    }
}
