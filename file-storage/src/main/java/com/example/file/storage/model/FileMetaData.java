package com.example.file.storage.model;

public class FileMetaData {

    private String id;
    private Long size;
    private String name;

    public FileMetaData() {
    }

    public String getId() {
        return id;
    }

    public Long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }
}
