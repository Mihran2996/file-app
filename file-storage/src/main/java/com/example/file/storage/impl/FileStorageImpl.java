package com.example.file.storage.impl;

import com.example.file.storage.handler.PropertiesHandler;
import com.example.file.storage.model.FileMetaData;
import com.example.file.storage.util.FileUtil;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileStorageImpl implements FileStorage {

    private static final Logger logger = Logger.getLogger(FileStorageImpl.class.getName());
    private static final String FILE_STORAGE_PATH_KEY = "PATH";
    private final String systemPath;

    public FileStorageImpl() {
        systemPath = new PropertiesHandler().getByKeyOrDefault(FILE_STORAGE_PATH_KEY, getDefaultPath());
    }

    @Override
    public FileMetaData upload(Part filePart) throws IOException {
        String fileName = FileUtil.getSubmittedFileName(filePart);
        String fullPath = systemPath + File.separator + fileName;

        Files.copy(filePart.getInputStream(), new File(fullPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        File file = new File(systemPath.concat("/").concat(fileName));
        FileMetaData fileMetaData = FileUtil.createFileMetaData(file);
        logger.log(Level.INFO, "File uploaded: {0}", fileMetaData);
        return fileMetaData;
    }

    @Override
    public String downloadById(Long id) throws FileNotFoundException {
        File[] files = createFileWithSystemPath();

        if (files == null) {
            FileNotFoundException exception = new FileNotFoundException("Directory does not exist or is not accessible");
            logger.log(Level.SEVERE, "Failed to download file by ID", exception);
            throw exception;
        }
        Optional<File> fileById = Stream.of(files)
                .filter(File::isFile)
                .filter(File::exists)
                .filter(file -> FileUtil.extractNumber(file.getName()).equals(id))
                .findFirst();
        return fileById.map(File::getAbsolutePath).orElseThrow(FileNotFoundException::new);
    }

    @Override
    public List<FileMetaData> retrieveAll() {
        File[] files = createFileWithSystemPath();

        List<FileMetaData> fileMetaData = files == null ? List.of() : Arrays.stream(files)
                .filter(File::isFile)
                .map(FileUtil::createFileMetaData).toList();

        logger.log(Level.INFO, "Retrieved {0} file(s)", fileMetaData.size());
        return fileMetaData;
    }

    private String getDefaultPath() {
        return System.getProperty("user.home");
    }

    private File[] createFileWithSystemPath() {
        File directory = new File(systemPath);
        File[] files = directory.listFiles();
        return files;
    }
}
