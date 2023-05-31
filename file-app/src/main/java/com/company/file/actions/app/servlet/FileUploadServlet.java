package com.company.file.actions.app.servlet;

import com.company.file.actions.app.convert.FileMetaDataConverter;
import com.company.file.actions.app.validator.FileValidator;
import com.example.file.storage.impl.FileStorage;
import com.example.file.storage.model.FileMetaData;
import com.example.file.storage.provider.FailStorageRetriever;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        Optional<FileStorage> fileStorage = FailStorageRetriever.loadFileStorage();
        if (fileStorage.isPresent()) {
            FileStorage fileStorageImpl = fileStorage.get();
            Part filePart = request.getPart("file");
            if (FileValidator.isFileMeetRequirements(filePart)) {
                FileMetaData fileMetaData = fileStorageImpl.upload(filePart);
                response.setContentType("application/json");
                response.getWriter().write(FileMetaDataConverter.convertDataToJson(fileMetaData));
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file uploaded");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("File storage provider not found");
        }
    }
}
