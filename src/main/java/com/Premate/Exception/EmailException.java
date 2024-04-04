package com.Premate.Exception;

public class EmailException extends RuntimeException {

    public EmailException() {
        super("An error occurred while sending email.");
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
