package com.company.file.actions.app.servlet;


import com.example.file.storage.impl.FileStorage;
import com.example.file.storage.provider.FailStorageRetriever;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDownloadServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(FileDownloadServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<FileStorage> fileStorage = FailStorageRetriever.loadFileStorage();
        if (fileStorage.isPresent()) {
            String idParam = request.getParameter("file_id");
            if (idParam != null) {
                try {
                    Long id = Long.parseLong(idParam);
                    FileStorage fileStorageImpl = fileStorage.get();
                    String filePath = fileStorageImpl.downloadById(id);
                    sendFile(response, filePath);
                    logger.log(Level.INFO, "File downloaded: " + filePath);
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Invalid file ID: " + idParam);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file ID");
                } catch (FileNotFoundException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                    logger.log(Level.WARNING, "File not found for ID: " + idParam);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File ID is missing");
                logger.log(Level.WARNING, "File ID is missing");
            }
        }
    }

    private void sendFile(HttpServletResponse response, String filePath) throws IOException {
        File file = new File(filePath);
        BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        if (file.exists() && basicFileAttributes.isRegularFile()) {
            response.setContentType("application/octet-stream");
            response.setContentLength((int) basicFileAttributes.size());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            try (InputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            logger.log(Level.WARNING, "File not found: " + filePath);
        }
    }
}

