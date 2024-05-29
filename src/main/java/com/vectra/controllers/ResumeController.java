package com.vectra.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vectra.services.ResumeService;

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
        resumeService.saveResume(resumeJson, username);
        return ResponseEntity.ok("Resume uploaded successfully");
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
}