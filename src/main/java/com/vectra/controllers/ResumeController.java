package com.vectra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vectra.services.ResumeService;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestBody String resumeJson, @RequestParam String username) {
        try {
            resumeService.saveResume(resumeJson, username);
            return ResponseEntity.ok("Resume uploaded successfully");
        } catch (IOException e) {
            System.out.println("Error occurred while uploading resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/missed_skills")
    public ResponseEntity<Map<String, List<String>>> getMissedSkills(@RequestParam String username, @RequestBody Map<String, String> jobOffer) {
        try {
            List<String> skills = resumeService.getSkillsFromResume(username);
            Map<String, List<String>> missedSkills = resumeService.getMissedSkills(skills, jobOffer.get("job_title"), jobOffer.get("job_description"), jobOffer.get("company"));
            return ResponseEntity.ok(missedSkills);
        } catch (IOException e) {

            System.out.println("error ioException : " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/enhance")
    public ResponseEntity<String> enhanceResume(@RequestParam String username, @RequestBody Map<String, Object> enhancementDetails) {
        try {
            resumeService.enhanceResume(username, enhancementDetails);
            return ResponseEntity.ok("Resume enhanced successfully");
        } catch (IOException e) {
            System.out.println("Error occurred while enhancing resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> downloadResume(@RequestParam String username, @RequestBody Map<String, String> details) {
        String jobTitle = details.get("job_title").replace(" ", "_");
        String companyName = details.get("company").replace(" ", "_");
        String format = details.get("format");

        String filename = username + "_" + jobTitle + "_" + companyName + "." + format;

        // Call the method to convert the JSON resume to the requested format and store it
        // This method is not implemented in this example
        // convertAndStoreResume(filename);

        // Get the file from the resumes directory
        File file = new File("./resumes/" + filename);

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            // Determine the content type
            String contentType = "application/octet-stream";
            if ("pdf".equals(format)) {
                contentType = "application/pdf";
            } else if ("latex".equals(format)) {
                contentType = "application/x-latex";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while downloading resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}