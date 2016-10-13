package me.itache.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String error) {
        super(error);
    }

    public ServiceException(String error, Exception e) {
        super(error,e);
    }

    public ServiceException(Exception e) {
        super(e);
    }
}
