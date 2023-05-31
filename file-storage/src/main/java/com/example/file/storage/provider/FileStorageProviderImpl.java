package com.example.file.storage.provider;

import com.example.file.storage.impl.FileStorage;
import com.example.file.storage.impl.FileStorageImpl;
import com.example.file.storage.spi.FileStorageProvider;

public class FileStorageProviderImpl implements FileStorageProvider {

    @Override
    public FileStorage createFileStorage() {
        return new FileStorageImpl();
    }
}
