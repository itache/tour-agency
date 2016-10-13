package me.itache.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String err) {
        super(err);
    }
}
