package com.myntra.core.exceptions;

import com.myntra.utils.logger.ILogger;

public class NoTestDataException extends Exception implements ILogger {
    public NoTestDataException(String message) {
        super(message);
    }
}
