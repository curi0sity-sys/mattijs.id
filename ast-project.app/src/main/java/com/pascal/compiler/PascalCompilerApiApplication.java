package com.pascal.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application for Pascal Compiler REST API
 * Based on "Writing Compilers and Interpreters" (3rd Edition) by Ronald Mak
 * 
 * @author Pascal Compiler API Team
 * @version 1.0.0
 */
@SpringBootApplication
public class PascalCompilerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PascalCompilerApiApplication.class, args);
    }
}

