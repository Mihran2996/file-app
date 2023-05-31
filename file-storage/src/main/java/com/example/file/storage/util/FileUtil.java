package com.example.file.storage.util;

import com.example.file.storage.model.FileMetaData;

import javax.servlet.http.Part;
import java.io.File;
import java.util.UUID;

public class FileUtil {

    public static String getSubmittedFileName(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        return addUniqueID(fileName);
    }

    public static Long extractNumber(String filename) {
        String numberString = filename.replaceAll("[^\\d]", "");
        return Long.parseLong(numberString);
    }

    public static FileMetaData createFileMetaData(File file) {
        FileMetaData fileMetaData = new FileMetaData();
        fileMetaData.setId(String.valueOf(FileUtil.extractNumber(file.getName())));
        fileMetaData.setSize(file.length());
        fileMetaData.setName(file.getName());
        return fileMetaData;
    }

    private static String addUniqueID(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        int dotIndex = fileName.lastIndexOf(".");

        String name = fileName.substring(0, dotIndex);
        String extension = fileName.substring(dotIndex);

        UUID uniqueId = UUID.randomUUID();
        long absNumber = Math.abs(uniqueId.getMostSignificantBits());

        long cappedNumber = Math.min(absNumber, Long.MAX_VALUE);
        stringBuilder.append(name).append("_").append(cappedNumber).append(extension);
        return stringBuilder.toString();
    }
}
