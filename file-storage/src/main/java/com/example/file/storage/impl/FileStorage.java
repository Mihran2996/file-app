package com.example.file.storage.impl;

import com.example.file.storage.model.FileMetaData;

import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileStorage {

    FileMetaData upload(Part filePart) throws IOException;

    String downloadById(Long id) throws FileNotFoundException;

    List<FileMetaData> retrieveAll() throws IOException;
}
