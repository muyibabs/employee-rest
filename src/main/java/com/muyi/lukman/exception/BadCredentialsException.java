package com.muyi.lukman.exception;

public class BadCredentialsException extends MicroServiceException {
    private static final long serialVersionUID = 1L;

    public BadCredentialsException(String errorCode, String message) {
        super(errorCode, message);
    }
}
