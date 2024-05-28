package com.vectra.services;

import com.vectra.entities.Resume;
import com.vectra.repositories.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final JsonFileService jsonFileService;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, JsonFileService jsonFileService) {
        this.resumeRepository = resumeRepository;
        this.jsonFileService = jsonFileService;
    }

    public Resume updateResume(Resume resume) {
        // Add business logic to update a resume
        return resumeRepository.save(resume);
    }

    public void updateResumeProperty(String filePath, String property, Object newValue) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }

        Map<String, Object> data = jsonFileService.readJsonFile(filePath);

        if (!data.containsKey(property)) {
            throw new IllegalArgumentException("Property does not exist in the JSON file: " + property);
        }

        data.put(property, newValue);
        jsonFileService.writeJsonFile(filePath, data);
    }

    // Other methods...
}