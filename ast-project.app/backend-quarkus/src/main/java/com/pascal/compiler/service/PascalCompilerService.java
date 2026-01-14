package com.pascal.compiler.service;

import com.pascal.compiler.api.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class PascalCompilerService {

    private static final Logger LOG = Logger.getLogger(PascalCompilerService.class);

    public ListResponse listFiles() {
        LOG.info("Listing Pascal files");
        return ListResponse.builder()
                .files(List.of())
                .totalCount(0)
                .build();
    }

    public ViewResponse viewFile(String filename) {
        LOG.infof("Viewing file: %s", filename);
        return ViewResponse.builder()
                .filename(filename)
                .sourceCode("")
                .lineCount(0)
                .build();
    }

    public CompileResponse compileFile(String filename) {
        LOG.infof("Compiling file: %s", filename);
        return CompileResponse.builder()
                .filename(filename)
                .success(true)
                .compiledOutput("")
                .jasminCode("")
                .errors("")
                .compilationTimeMs(0)
                .build();
    }

}

    
            
            