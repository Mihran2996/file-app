package com.company;

import com.company.file.actions.app.server.JettyServer;

public class Main {
    public static void main(String[] args) throws Exception {
        JettyServer jettyServer = new JettyServer();
        jettyServer.start();
    }
}
