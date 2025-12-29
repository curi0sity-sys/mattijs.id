package com.pascal.compiler.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Pascal file information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PascalFileDto {
    private String filename;
    private String displayName;
    private String description;
}

