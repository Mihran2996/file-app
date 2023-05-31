package com.company.file.actions.app.validator;


import javax.servlet.http.Part;

public class FileValidator {

    private static final String TXT_TYPE = ".txt";
    private static final String CSV_TYPE = ".csv";
    private static final int MAX_FILE_SIZE = 100 * 1024;

    public static boolean isFileMeetRequirements(Part part) {
        return isFileNull(part) && isFileTypeValid(part) && isFileSizeValid(part);
    }

    private static boolean isFileNull(Part filePart) {
        return filePart != null;
    }

    private static boolean isFileTypeValid(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        return fileName.endsWith(TXT_TYPE) || fileName.endsWith(CSV_TYPE);
    }

    private static boolean isFileSizeValid(Part filePart) {
        return filePart.getSize() > 0 && filePart.getSize() <= MAX_FILE_SIZE;
    }
}
