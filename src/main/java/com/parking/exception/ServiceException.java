package com.parking.exception;

import java.text.MessageFormat;

public class ServiceException  extends Exception{
    public ServiceException() {
        super();
    }

    public ServiceException(String message, Object... arguments) {
        super(MessageFormat.format(message, arguments));
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
