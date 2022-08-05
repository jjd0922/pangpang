package com.newper.exception;

/** 로그인 정보 없을 때 던지는 exception*/
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
