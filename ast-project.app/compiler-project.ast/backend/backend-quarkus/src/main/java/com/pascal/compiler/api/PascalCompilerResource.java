package com.pascal.compiler.api;

import com.pascal.compiler.api.dto.*;
import com.pascal.compiler.service.PascalCompilerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PascalCompilerResource {

    @Inject
    PascalCompilerService compilerService;

    @GET
    @Path("/list")
    public ListResponse listFiles() {
        return compilerService.listFiles();
    }

    @GET
    @Path("/view/{filename}")
    public ViewResponse viewFile(@PathParam("filename") String filename) {
        return compilerService.viewFile(filename);
    }

    @POST
    @Path("/compile/{filename}")
    public CompileResponse compileFile(@PathParam("filename") String filename) {
        return compilerService.compileFile(filename);
    }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "Pascal Compiler API is running";
    }
}
