package com.pascal.compiler.api.dto;

public class CompileResponse {
    public String filename;
    public boolean success;
    public String compiledOutput;
    public String jasminCode;
    public String errors;
    public long compilationTimeMs;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CompileResponse r = new CompileResponse();

        public Builder filename(String filename) {
            r.filename = filename;
            return this;
        }

        public Builder success(boolean success) {
            r.success = success;
            return this;
        }

        public Builder compiledOutput(String compiledOutput) {
            r.compiledOutput = compiledOutput;
            return this;
        }

        public Builder jasminCode(String jasminCode) {
            r.jasminCode = jasminCode;
            return this;
        }

        public Builder errors(String errors) {
            r.errors = errors;
            return this;
        }

        public Builder compilationTimeMs(long ms) {
            r.compilationTimeMs = ms;
            return this;
        }

        public CompileResponse build() {
            return r;
        }
    }
}
