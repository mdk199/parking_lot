package com.parking.exception;

import java.text.MessageFormat;

public class ManagerException extends Exception {
    public ManagerException() {
        super();
    }

    public ManagerException(String message, Object... arguments) {
        super(MessageFormat.format(message, arguments));
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }

    protected ManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
