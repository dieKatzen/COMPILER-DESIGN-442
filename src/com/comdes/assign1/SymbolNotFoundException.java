package com.comdes.assign1;

public class SymbolNotFoundException extends Exception {
    public SymbolNotFoundException() {
        System.out.println("Symbol not found!");
    }

    public SymbolNotFoundException(String message) {
        super(message);
    }

    public SymbolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SymbolNotFoundException(Throwable cause) {
        super(cause);
    }

    public SymbolNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
