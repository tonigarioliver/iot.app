package com.ago.iotapp.web.service.exception;

public class ItemAlreadyExists extends Exception{
    public ItemAlreadyExists() {
    }

    public ItemAlreadyExists(String message) {
        super(message);
    }

    public ItemAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemAlreadyExists(Throwable cause) {
        super(cause);
    }

    public ItemAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
