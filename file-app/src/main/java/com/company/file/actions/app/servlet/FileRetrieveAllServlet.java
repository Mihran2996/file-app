package com.company.file.actions.app.servlet;

import com.company.file.actions.app.convert.FileMetaDataConverter;
import com.example.file.storage.impl.FileStorage;
import com.example.file.storage.model.FileMetaData;
import com.example.file.storage.provider.FailStorageRetriever;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileRetrieveAllServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(FileRetrieveAllServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<FileStorage> fileStorage = FailStorageRetriever.loadFileStorage();
        if (fileStorage.isPresent()) {
            FileStorage fileStorageImpl = fileStorage.get();
            List<FileMetaData> fileMetaDatesFromSystem = fileStorageImpl.retrieveAll();

            logger.log(Level.INFO, "Retrieved all file metadata from the storage system");
            response.setContentType("application/json");
            try (PrintWriter writer = response.getWriter()) {
                writer.write(FileMetaDataConverter.convertDataToJson(fileMetaDatesFromSystem));
            }
        } else {
            logger.log(Level.SEVERE, "File storage provider not found");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("File storage provider not found");
        }
    }
}
