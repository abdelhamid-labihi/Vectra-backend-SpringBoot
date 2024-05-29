package com.vectra.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class PdfLatexService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> readJsonFile(String filePath) throws IOException {
        return objectMapper.readValue(Paths.get(filePath).toFile(), Map.class);
    }

    public void writeJsonFile(String filePath, Map<String, Object> data) throws IOException {
        objectMapper.writeValue(Paths.get(filePath).toFile(), data);
    }
}