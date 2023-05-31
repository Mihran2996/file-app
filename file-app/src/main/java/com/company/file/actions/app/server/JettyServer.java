package com.company.file.actions.app.server;

import com.company.file.actions.app.servlet.FileDownloadServlet;
import com.company.file.actions.app.servlet.FileRetrieveAllServlet;
import com.company.file.actions.app.servlet.FileUploadServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.MultipartConfigElement;

public class JettyServer {

    public void start() throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(FileUploadServlet.class, "/files/upload/*")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("./tmp"));

        context.addServlet(FileDownloadServlet.class, "/files/download/*");
        context.addServlet(FileRetrieveAllServlet.class, "/files");

        server.setHandler(context);
        server.start();
        server.join();
    }
}
