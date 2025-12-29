package com.pascal.compiler.service;

import com.pascal.compiler.api.dto.CompileResponse;
import com.pascal.compiler.api.dto.ListResponse;
import com.pascal.compiler.api.dto.PascalFileDto;
import com.pascal.compiler.api.dto.ViewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import wci.backend.Backend;
import wci.backend.BackendFactory;
import wci.frontend.FrontendFactory;
import wci.frontend.Parser;
import wci.frontend.Source;
import wci.intermediate.ICode;
import wci.intermediate.SymTabEntry;
import wci.intermediate.SymTabStack;
import wci.message.Message;
import wci.message.MessageListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_ICODE;

/**
 * Service for Pascal compiler operations
 */
@Slf4j
@Service
public class PascalCompilerService {

    private static final Map<String, PascalFileDto> WHITELISTED_FILES = new HashMap<>();

    static {
        WHITELISTED_FILES.put("HelloOnce.pas", new PascalFileDto(
            "HelloOnce.pas", "Hello Once", "Simple hello world program"));
        WHITELISTED_FILES.put("HelloMany.pas", new PascalFileDto(
            "HelloMany.pas", "Hello Many", "Hello world with loop"));
        WHITELISTED_FILES.put("factorial.pas", new PascalFileDto(
            "factorial.pas", "Factorial", "Factorial calculation"));
        WHITELISTED_FILES.put("newton1.pas", new PascalFileDto(
            "newton1.pas", "Newton's Method", "Square root using Newton's method"));
        WHITELISTED_FILES.put("ForTest.pas", new PascalFileDto(
            "ForTest.pas", "For Loop Test", "Testing FOR loops"));
        WHITELISTED_FILES.put("IfTest.pas", new PascalFileDto(
            "IfTest.pas", "If Statement Test", "Testing IF statements"));
        WHITELISTED_FILES.put("Xref.pas", new PascalFileDto(
            "Xref.pas", "Cross Reference", "Cross-reference generator from the book"));
    }

    @Value("${pascal.compiler.examples-path}")
    private String examplesPath;

    /**
     * List all whitelisted Pascal files
     */
    public ListResponse listFiles() {
        List<PascalFileDto> files = new ArrayList<>(WHITELISTED_FILES.values());
        return ListResponse.builder()
            .files(files)
            .totalCount(files.size())
            .build();
    }

    /**
     * View source code of a Pascal file
     */
    public ViewResponse viewFile(String filename) {
        validateFilename(filename);
        
        try {
            String sourceCode = loadFileContent(filename);
            int lineCount = (int) sourceCode.lines().count();
            
            return ViewResponse.builder()
                .filename(filename)
                .sourceCode(sourceCode)
                .lineCount(lineCount)
                .build();
        } catch (IOException e) {
            log.error("Error reading file: {}", filename, e);
            throw new PascalFileNotFoundException(filename);
        }
    }

    /**
     * Compile a Pascal file and return the results
     */
    public CompileResponse compileFile(String filename) {
        validateFilename(filename);
        
        long startTime = System.currentTimeMillis();
        OutputCapture capture = new OutputCapture();
        
        try {
            String sourceCode = loadFileContent(filename);
            
            // Start capturing output
            capture.start();
            
            // Create the source with BufferedReader
            Source source = new Source(new BufferedReader(new StringReader(sourceCode)));
            source.addMessageListener(new SourceMessageListener());
            
            // Create the parser
            Parser parser = FrontendFactory.createParser("Pascal", "top-down", source);
            parser.addMessageListener(new ParserMessageListener());
            
            // Parse the source
            parser.parse();
            source.close();
            
            // Get intermediate code and symbol table
            SymTabStack symTabStack = parser.getSymTabStack();
            SymTabEntry programId = symTabStack.getProgramId();
            ICode iCode = (ICode) programId.getAttribute(ROUTINE_ICODE);
            
            // Create the backend (compiler)
            Backend backend = BackendFactory.createBackend("compile");
            backend.addMessageListener(new BackendMessageListener());
            
            // Process the intermediate code
            backend.process(iCode, symTabStack);
            
            // Stop capturing
            capture.stop();
            
            String output = capture.getOutput();
            long compilationTime = System.currentTimeMillis() - startTime;
            
            // Extract statistics from output
            CompileResponse.CompilationStats stats = extractStats(output);
            
            // Read the generated Jasmin file
            String jasminCode = readGeneratedJasminFile(programId.getName());
            
            return CompileResponse.builder()
                .filename(filename)
                .success(true)
                .pascalSource(sourceCode)
                .compiledOutput(output)
                .jasminCode(jasminCode)
                .errors("")
                .compilationTimeMs(compilationTime)
                .stats(stats)
                .build();
                
        } catch (Exception e) {
            capture.stop();
            log.error("Compilation error for file: {}", filename, e);
            
            long compilationTime = System.currentTimeMillis() - startTime;
            
            String sourceCode = "";
            try {
                sourceCode = loadFileContent(filename);
            } catch (Exception ignored) {}
            
            return CompileResponse.builder()
                .filename(filename)
                .success(false)
                .pascalSource(sourceCode)
                .compiledOutput(capture.getOutput())
                .jasminCode("")
                .errors(e.getMessage())
                .compilationTimeMs(compilationTime)
                .build();
        }
    }

    /**
     * Validate that the filename is whitelisted
     */
    private void validateFilename(String filename) {
        if (!WHITELISTED_FILES.containsKey(filename)) {
            throw new PascalFileNotFoundException(filename);
        }
    }

    /**
     * Load file content from resources
     */
    private String loadFileContent(String filename) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("classpath:pascal-examples/" + filename);
        
        if (!resource.exists()) {
            throw new IOException("File not found: " + filename);
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Extract compilation statistics from output
     */
    private CompileResponse.CompilationStats extractStats(String output) {
        CompileResponse.CompilationStats.CompilationStatsBuilder builder = 
            CompileResponse.CompilationStats.builder();
        
        // Parse output for statistics
        String[] lines = output.split("\n");
        for (String line : lines) {
            if (line.contains("source lines")) {
                try {
                    int sourceLines = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    builder.sourceLines(sourceLines);
                } catch (NumberFormatException e) {
                    // Ignore parsing errors
                }
            } else if (line.contains("syntax errors")) {
                try {
                    int errors = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    builder.syntaxErrors(errors);
                } catch (NumberFormatException e) {
                    // Ignore parsing errors
                }
            } else if (line.contains("seconds")) {
                try {
                    String timeStr = line.replaceAll("[^0-9.]", "");
                    float time = Float.parseFloat(timeStr);
                    builder.elapsedTime(time);
                } catch (NumberFormatException e) {
                    // Ignore parsing errors
                }
            }
        }
        
        return builder.build();
    }

    /**
     * Read the generated Jasmin file
     */
    private String readGeneratedJasminFile(String programName) {
        try {
            String jasminFileName = programName + ".j";
            File jasminFile = new File(jasminFileName);
            
            if (jasminFile.exists()) {
                String content = Files.readString(Paths.get(jasminFileName), StandardCharsets.UTF_8);
                // Clean up the generated file
                jasminFile.delete();
                return content;
            } else {
                log.warn("Jasmin file not found: {}", jasminFileName);
                return "// Jasmin file not generated";
            }
        } catch (IOException e) {
            log.error("Error reading Jasmin file", e);
            return "// Error reading Jasmin file: " + e.getMessage();
        }
    }

    /**
     * Message listener for source
     */
    private static class SourceMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            log.debug("Source message: {}", message.getType());
        }
    }

    /**
     * Message listener for parser
     */
    private static class ParserMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            log.debug("Parser message: {}", message.getType());
        }
    }

    /**
     * Message listener for backend
     */
    private static class BackendMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            log.debug("Backend message: {}", message.getType());
        }
    }
}

