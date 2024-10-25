package com.pangpang.exception;

/** ~.load 로 ajax요청 후 mav return 하는 메서드들에서 에러 발생시 msg alert */
public class AlertException extends RuntimeException {
    public AlertException() {
        super();
    }

    public AlertException(String message) {
        super(message);
    }

    public AlertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlertException(Throwable cause) {
        super(cause);
    }
}
