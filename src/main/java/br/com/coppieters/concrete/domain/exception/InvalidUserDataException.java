package br.com.coppieters.concrete.domain.exception;

public class InvalidUserDataException extends Exception {
    public InvalidUserDataException() {
        super();
    }

    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserDataException(Throwable cause) {
        super(cause);
    }

    protected InvalidUserDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
