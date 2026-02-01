package com.pascal.compiler.service;

import com.pascal.compiler.api.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import wci.backend.Backend;
import wci.backend.BackendFactory;
import wci.frontend.FrontendFactory;
import wci.frontend.Parser;
import wci.frontend.Source;
import wci.intermediate.ICode;
import wci.intermediate.SymTabEntry;
import wci.intermediate.SymTabStack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_ICODE;

@ApplicationScoped
public class PascalCompilerService {

    private static final Logger LOG = Logger.getLogger(PascalCompilerService.class);

    @ConfigProperty(name = "pascal.compiler.examples-path", defaultValue = "pascal-examples")
    String examplesPath;

    private static final Map<String, PascalFileDto> WHITELISTED_FILES = Map.of(
            "HelloOnce.pas", new PascalFileDto("HelloOnce.pas", "Hello Once", "Simple hello world"),
            "HelloMany.pas", new PascalFileDto("HelloMany.pas", "Hello Many", "Loop example"),
            "factorial.pas", new PascalFileDto("factorial.pas", "Factorial", "Factorial calculation"),
            "newton1.pas", new PascalFileDto("newton1.pas", "Newton", "Newton method"),
            "ForTest.pas", new PascalFileDto("ForTest.pas", "For Test", "For loop test"),
            "IfTest.pas", new PascalFileDto("IfTest.pas", "If Test", "If test"),
            "Xref.pas", new PascalFileDto("Xref.pas", "Xref", "Cross reference"));

    // -------- LIST --------
    public ListResponse listFiles() {
        return ListResponse.builder()
                .files(new ArrayList<>(WHITELISTED_FILES.values()))
                .totalCount(WHITELISTED_FILES.size())
                .build();
    }

    // -------- VIEW --------
    public ViewResponse viewFile(String filename) {
        validate(filename);
        try {
            String source = loadFile(filename);
            return ViewResponse.builder()
                    .filename(filename)
                    .sourceCode(source)
                    .lineCount((int) source.lines().count())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // -------- COMPILE --------
    public CompileResponse compileFile(String filename) {
        validate(filename);
        long start = System.currentTimeMillis();
        OutputCapture capture = new OutputCapture();

        try {
            String sourceCode = loadFile(filename);
            capture.start();

            Source source = new Source(new BufferedReader(new StringReader(sourceCode)));
            Parser parser = FrontendFactory.createParser("Pascal", "top-down", source);
            parser.parse();

            SymTabStack symTabStack = parser.getSymTabStack();
            SymTabEntry programId = symTabStack.getProgramId();
            ICode iCode = (ICode) programId.getAttribute(ROUTINE_ICODE);

            Backend backend = BackendFactory.createBackend("compile");
            backend.process(iCode, symTabStack);

            capture.stop();

            return CompileResponse.builder()
                    .filename(filename)
                    .success(true)
                    .compiledOutput(capture.getOutput())
                    .jasminCode(readJasmin(programId.getName()))
                    .compilationTimeMs(System.currentTimeMillis() - start)
                    .build();

        } catch (Exception e) {
            capture.stop();
            return CompileResponse.builder()
                    .filename(filename)
                    .success(false)
                    .errors(e.getMessage())
                    .compiledOutput(capture.getOutput())
                    .compilationTimeMs(System.currentTimeMillis() - start)
                    .build();
        }
    }

    // -------- HELPERS --------
    private void validate(String filename) {
        if (!WHITELISTED_FILES.containsKey(filename)) {
            throw new RuntimeException("Invalid file: " + filename);
        }
    }

    private String loadFile(String filename) throws IOException {
        String resourcePath = examplesPath + "/" + filename;
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new FileNotFoundException(resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private String readJasmin(String programName) throws IOException {
        Path jasmin = Path.of(programName + ".j");
        if (!Files.exists(jasmin)) {
            return "// not generated";
        }
        String content = Files.readString(jasmin);
        Files.delete(jasmin);
        return content;
    }

    // -------- OUTPUT CAPTURE --------
    static class OutputCapture {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();
        private PrintStream original;

        void start() {
            original = System.out;
            System.setOut(new PrintStream(out));
        }

        void stop() {
            System.setOut(original);
        }

        String getOutput() {
            return out.toString();
        }
    }
}
