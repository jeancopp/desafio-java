package br.com.coppieters.concrete.domain.exception;

public class UserRecordedPreviouslyException extends Exception{
    public UserRecordedPreviouslyException() {
        super();
    }

    public UserRecordedPreviouslyException(String message) {
        super(message);
    }

    public UserRecordedPreviouslyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRecordedPreviouslyException(Throwable cause) {
        super(cause);
    }

    protected UserRecordedPreviouslyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
