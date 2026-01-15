package com.pascal.compiler.api.dto;

public class PascalFileDto {

    private String filename;
    private String title;
    private String description;

    public PascalFileDto() {
    }

    public PascalFileDto(String filename, String title, String description) {
        this.filename = filename;
        this.title = title;
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
