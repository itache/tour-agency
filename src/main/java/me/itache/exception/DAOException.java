package me.itache.exception;

public class DAOException extends RuntimeException {
    public DAOException(Exception ex) {
        super(ex);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String error, Exception ex) {
        super(error, ex);
    }
}