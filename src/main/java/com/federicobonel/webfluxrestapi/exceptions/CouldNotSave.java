package com.federicobonel.webfluxrestapi.exceptions;

public class CouldNotSave extends RuntimeException {
    public CouldNotSave() {
        super();
    }

    public CouldNotSave(String message) {
        super(message);
    }

    public CouldNotSave(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotSave(Throwable cause) {
        super(cause);
    }

    protected CouldNotSave(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
