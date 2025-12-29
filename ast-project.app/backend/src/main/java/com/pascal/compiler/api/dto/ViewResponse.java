package com.pascal.compiler.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for viewing Pascal source code
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewResponse {
    private String filename;
    private String sourceCode;
    private int lineCount;
}

