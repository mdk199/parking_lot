package com.exception;

import java.text.MessageFormat;

public class ParkingException extends Exception {
    public ParkingException() {
        super();
    }

    public ParkingException(String message, Object... arguments) {
        super(MessageFormat.format(message, arguments));
    }

    public ParkingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingException(Throwable cause) {
        super(cause);
    }

    protected ParkingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
