package com.pascal.compiler.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for listing available Pascal files
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse {
    private List<PascalFileDto> files;
    private int totalCount;
}

