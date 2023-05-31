package com.company.file.actions.app.convert;


import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FileMetaDataConverter {

    private static final Logger logger = Logger.getLogger(FileMetaDataConverter.class.getName());

    public static <T> String convertDataToJson(T data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        logger.log(Level.INFO, "Converted data to JSON: " + json);
        return json;
    }
}
