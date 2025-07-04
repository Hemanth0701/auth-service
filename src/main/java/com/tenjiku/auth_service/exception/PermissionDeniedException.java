package com.tenjiku.auth_service.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message ) {
        super(message);
    }
}
