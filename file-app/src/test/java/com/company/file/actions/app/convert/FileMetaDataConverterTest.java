package com.company.file.actions.app.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileMetaDataConverterTest {

    @Test
    void testConvertDataToJson_WithValidData_ReturnsJsonString() {
        TestData testData = new TestData("John", 25);

        FileMetaDataConverter JsonConverter = null;
        String json = JsonConverter.convertDataToJson(testData);

        String expectedJson = "{\"name\":\"John\",\"age\":25}";
        Assertions.assertEquals(expectedJson, json);
    }

    @Test
    void testConvertDataToJson_WithEmptyObject_ReturnsJsonString() {
        TestEmptyData emptyData = new TestEmptyData();

        FileMetaDataConverter JsonConverter = null;
        String json = JsonConverter.convertDataToJson(emptyData);

        String expectedJson = "{}";
        Assertions.assertEquals(expectedJson, json);
    }

    private static class TestData {
        private String name;
        private int age;

        public TestData(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private static class TestEmptyData {
    }
}