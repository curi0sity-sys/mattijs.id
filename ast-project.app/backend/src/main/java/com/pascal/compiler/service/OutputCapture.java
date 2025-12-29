package com.pascal.compiler.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Utility class to capture System.out output
 */
public class OutputCapture {
    private final ByteArrayOutputStream outputStream;
    private final PrintStream originalOut;
    private final PrintStream captureStream;

    public OutputCapture() {
        this.outputStream = new ByteArrayOutputStream();
        this.originalOut = System.out;
        this.captureStream = new PrintStream(outputStream);
    }

    /**
     * Start capturing output
     */
    public void start() {
        System.setOut(captureStream);
    }

    /**
     * Stop capturing output and restore original System.out
     */
    public void stop() {
        System.setOut(originalOut);
        captureStream.flush();
    }

    /**
     * Get the captured output as a string
     */
    public String getOutput() {
        return outputStream.toString();
    }

    /**
     * Clear the captured output
     */
    public void clear() {
        outputStream.reset();
    }

    /**
     * Auto-closeable wrapper for try-with-resources
     */
    public static AutoCloseable capture(OutputCaptureCallback callback) {
        OutputCapture capture = new OutputCapture();
        capture.start();
        
        return () -> {
            capture.stop();
            callback.accept(capture.getOutput());
        };
    }

    @FunctionalInterface
    public interface OutputCaptureCallback {
        void accept(String output);
    }
}

