package com.pascal.compiler.api.dto;

public class ViewResponse {
    public String filename;
    public String sourceCode;
    public int lineCount;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ViewResponse r = new ViewResponse();

        public Builder filename(String filename) {
            r.filename = filename;
            return this;
        }

        public Builder sourceCode(String sourceCode) {
            r.sourceCode = sourceCode;
            return this;
        }

        public Builder lineCount(int lineCount) {
            r.lineCount = lineCount;
            return this;
        }

        public ViewResponse build() {
            return r;
        }
    }
}
