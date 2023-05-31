package com.example.file.storage.provider;

import com.example.file.storage.impl.FileStorage;
import com.example.file.storage.spi.FileStorageProvider;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FailStorageRetriever {

    private static final Logger logger = Logger.getLogger(FailStorageRetriever.class.getName());
    private static final String FILE_STORAGE_PROVIDER_NAME = "com.example.file.storage.provider.FileStorageProviderImpl";

    public static Optional<FileStorage> loadFileStorage() {
        Optional<FileStorageProvider> fileStorageProvider = ServiceLoader.load(FileStorageProvider.class)
                .stream()
                .filter(provider -> provider.type().getName().equals(FILE_STORAGE_PROVIDER_NAME))
                .map(ServiceLoader.Provider::get)
                .findFirst();

        if (fileStorageProvider.isPresent()) {
            FileStorage fileStorage = fileStorageProvider.get().createFileStorage();
            logger.log(Level.INFO, "File storage loaded successfully");
            return Optional.of(fileStorage);
        }
        logger.log(Level.SEVERE, "Failed to load file storage");
        return Optional.empty();
    }
}
