package com.pangpang.exception;

public class NoRollbackException extends RuntimeException{

    public NoRollbackException() {
        super();
    }

    public NoRollbackException(String message) {
        super(message);
    }

    public NoRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRollbackException(Throwable cause) {
        super(cause);
    }
}
