package com.pangpang.exception;

/** shop에서 사용되는 휴면회원 익셉션*/
public class CuSleepException extends RuntimeException {

    public String id;


    public CuSleepException(String id) {
        this.id = id ;
    }

    public CuSleepException(String message, Throwable cause) {
        super(message, cause);
    }

    public CuSleepException(Throwable cause) {
        super(cause);
    }
}