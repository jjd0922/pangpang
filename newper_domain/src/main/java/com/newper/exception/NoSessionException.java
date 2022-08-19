package com.newper.exception;

public class NoSessionException extends RuntimeException{

    public NoSessionException() {
        super();
    }

    public NoSessionException(String message) {
        super(message);
    }

    public NoSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSessionException(Throwable cause) {
        super(cause);
    }
}
