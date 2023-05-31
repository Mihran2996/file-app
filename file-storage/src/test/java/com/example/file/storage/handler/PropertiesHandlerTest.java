package com.example.file.storage.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesHandlerTest {

    private PropertiesHandler propertiesHandler;

    @BeforeEach
    public void setUp() {
        propertiesHandler = new PropertiesHandler();
    }

    @Test
    public void testGetByKeyOrDefault_NonExistingKey_ReturnsDefaultValue() {
        String propertyKey = "nonExisting.key";
        String defaultValue = "default.value";

        String actualValue = propertiesHandler.getByKeyOrDefault(propertyKey, defaultValue);

        assertEquals(defaultValue, actualValue);
    }
}