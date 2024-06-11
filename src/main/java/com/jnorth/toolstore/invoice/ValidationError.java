package com.jnorth.toolstore.invoice;

public class ValidationError extends Exception {
    public ValidationError(String msg) {
        super(msg);
    }
}
