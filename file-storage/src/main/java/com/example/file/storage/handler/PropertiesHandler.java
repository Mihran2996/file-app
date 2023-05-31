package com.example.file.storage.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesHandler {

    private static final Logger logger = Logger.getLogger(PropertiesHandler.class.getName());
    private static final String APPLICATION_PROPERTIES_FILE_LOCATION = "META-INF/properties/application.properties";
    private static final String EMPTY_STRING = "";
    private final Properties properties;

    public PropertiesHandler() {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(APPLICATION_PROPERTIES_FILE_LOCATION);
        this.properties = new Properties();
        try {
            properties.load(inStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load properties file", e);
            throw new RuntimeException(e);
        }
    }

    public String getByKeyOrDefault(String propertyKey, String defaultValue) {
        String property = properties.getProperty(propertyKey);
        if (property != null && !Objects.equals(property, EMPTY_STRING)) {
            logger.log(Level.INFO, "Retrieved property for key: " + propertyKey);
            return property;
        }
        logger.log(Level.WARNING, "Property not found for key: " + propertyKey);
        return defaultValue;
    }
}
