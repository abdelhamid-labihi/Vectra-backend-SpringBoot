package com.vectra.services;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;


@Service
public class JsonFileService {

    public void writeJsonToFile(String filePath, String jsonContent) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file at path: " + filePath, e);
        }
    }
}