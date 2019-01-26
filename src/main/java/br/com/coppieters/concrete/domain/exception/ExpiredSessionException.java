package br.com.coppieters.concrete.domain.exception;

public class ExpiredSessionException extends Exception {
    public ExpiredSessionException() {
    }

    public ExpiredSessionException(String message) {
        super(message);
    }

    public ExpiredSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredSessionException(Throwable cause) {
        super(cause);
    }

    public ExpiredSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
