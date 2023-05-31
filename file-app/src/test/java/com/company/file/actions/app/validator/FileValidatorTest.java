package com.company.file.actions.app.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.Part;


class FileValidatorTest {

    @Test
    void testIsFileMeetRequirements_WithNullFilePart_ReturnsFalse() {
        Part part = null;
        boolean result = FileValidator.isFileMeetRequirements(part);
        Assertions.assertFalse(result);
    }

    @Test
    void testIsFileMeetRequirements_WithInvalidFileType_ReturnsFalse() {
        Part filePart = createFilePart("document.pdf");
        boolean result = FileValidator.isFileMeetRequirements(filePart);
        Assertions.assertFalse(result);
    }

    @Test
    void testIsFileMeetRequirements_WithEmptyFileName_ReturnsFalse() {
        Part filePart = createFilePart("");
        boolean result = FileValidator.isFileMeetRequirements(filePart);
        Assertions.assertFalse(result);
    }

    @Test
    void testIsFileMeetRequirements_WithBlankFileName_ReturnsFalse() {
        Part filePart = createFilePart("    ");
        boolean result = FileValidator.isFileMeetRequirements(filePart);
        Assertions.assertFalse(result);
    }


    private Part createFilePart(String fileName) {
        Part filePart = Mockito.mock(Part.class);
        Mockito.when(filePart.getSubmittedFileName()).thenReturn(fileName);
        return filePart;
    }
}