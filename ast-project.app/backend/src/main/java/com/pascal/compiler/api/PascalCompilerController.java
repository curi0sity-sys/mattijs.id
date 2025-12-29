package com.pascal.compiler.api;

import com.pascal.compiler.api.dto.CompileResponse;
import com.pascal.compiler.api.dto.ListResponse;
import com.pascal.compiler.api.dto.ViewResponse;
import com.pascal.compiler.service.PascalCompilerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Pascal Compiler API
 * Provides endpoints to view, compile, and list Pascal programs
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PascalCompilerController {

    private final PascalCompilerService compilerService;

    /**
     * List all available whitelisted Pascal files
     * 
     * @return List of available Pascal files
     */
    @GetMapping("/list")
    public ResponseEntity<ListResponse> listFiles() {
        log.info("GET /api/list - Listing all Pascal files");
        ListResponse response = compilerService.listFiles();
        return ResponseEntity.ok(response);
    }

    /**
     * View source code of a specific Pascal file
     * 
     * @param filename Name of the Pascal file
     * @return Source code content
     */
    @GetMapping("/view/{filename}")
    public ResponseEntity<ViewResponse> viewFile(@PathVariable String filename) {
        log.info("GET /api/view/{} - Viewing Pascal file", filename);
        ViewResponse response = compilerService.viewFile(filename);
        return ResponseEntity.ok(response);
    }

    /**
     * Compile a specific Pascal file and return the generated JVM bytecode
     * 
     * @param filename Name of the Pascal file
     * @return Compilation results including Jasmin code
     */
    @PostMapping("/compile/{filename}")
    public ResponseEntity<CompileResponse> compileFile(@PathVariable String filename) {
        log.info("POST /api/compile/{} - Compiling Pascal file", filename);
        CompileResponse response = compilerService.compileFile(filename);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Pascal Compiler API is running");
    }
}

