package com.example.file.storage.spi;

import com.example.file.storage.impl.FileStorage;

public interface FileStorageProvider {

    FileStorage createFileStorage();
}
