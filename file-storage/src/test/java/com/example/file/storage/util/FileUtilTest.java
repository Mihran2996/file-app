package com.example.file.storage.util;

import org.junit.jupiter.api.Test;


import javax.servlet.http.Part;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileUtilTest {

    @Test
    public void testGetSubmittedFileName() {
        Part mockPart = mock(Part.class);

        String submittedFileName = "path/to/filename.jpg";
        when(mockPart.getSubmittedFileName()).thenReturn(submittedFileName);

        String actual = FileUtil.getSubmittedFileName(mockPart);

        String expected = "filename.jpg";
        assertEquals(expected, extractFileName(actual));
    }

    @Test
    public void testExtractNumber() {
        String filename = "file12345.txt";
        Long extractedNumber = FileUtil.extractNumber(filename);
        assertEquals(Long.valueOf(12345L), extractedNumber);

        filename = "file_123_45.txt";
        extractedNumber = FileUtil.extractNumber(filename);
        assertEquals(Long.valueOf(12345L), extractedNumber);
    }


    private String extractFileName(String path) {
        int lastSlashIndex = path.lastIndexOf('/');

        String fileNameWithExtension = path.substring(lastSlashIndex + 1);
        int lastUnderscoreIndex = fileNameWithExtension.lastIndexOf('_');
        if (lastUnderscoreIndex >= 0) {
            return fileNameWithExtension.substring(0, lastUnderscoreIndex) +
                    fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf('.'));
        }
        return fileNameWithExtension;
    }

}