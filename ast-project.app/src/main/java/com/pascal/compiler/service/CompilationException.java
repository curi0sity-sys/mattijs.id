package com.pascal.compiler.service;

/**
 * Exception thrown when compilation fails
 */
public class CompilationException extends RuntimeException {
    public CompilationException(String message) {
        super(message);
    }

    public CompilationException(String message, Throwable cause) {
        super(message, cause);
    }
}

