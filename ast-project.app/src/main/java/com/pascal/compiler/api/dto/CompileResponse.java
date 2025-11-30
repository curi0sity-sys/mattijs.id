package com.pascal.compiler.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for compilation results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompileResponse {
    private String filename;
    private boolean success;
    private String pascalSource;
    private String compiledOutput;
    private String jasminCode;
    private String errors;
    private long compilationTimeMs;
    private CompilationStats stats;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompilationStats {
        private int sourceLines;
        private int syntaxErrors;
        private float elapsedTime;
        private int instructionsExecuted;
    }
}

