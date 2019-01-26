package br.com.coppieters.concrete.domain.exception;

public class UnauthorizedTokenException extends Exception{
    public UnauthorizedTokenException() {
    }

    public UnauthorizedTokenException(String message) {
        super(message);
    }

    public UnauthorizedTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedTokenException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
