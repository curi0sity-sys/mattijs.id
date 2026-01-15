package com.pascal.compiler.service;

/**
 * Exception thrown when a requested Pascal file is not found
 */
public class PascalFileNotFoundException extends RuntimeException {
    public PascalFileNotFoundException(String filename) {
        super("Pascal file not found: " + filename);
    }
}

