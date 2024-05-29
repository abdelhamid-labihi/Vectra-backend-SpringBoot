package com.vectra.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResumeService {

    @Autowired
    private JsonFileService jsonFileService;


    public void saveResume(String resumeJson, String username) throws IOException {
        String dirPath = "./resumes";
        String filePath = dirPath + "/" + username + ".json";

        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(resumeJson, Object.class);
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

        Files.write(Paths.get(filePath), prettyJson.getBytes());
    }


    public List<String> getSkillsFromResume(String username) throws IOException {
        String dirPath = "./resumes";
        String filePath = dirPath + "/" + username + ".json";
        String resumeJson = new String(Files.readAllBytes(Paths.get(filePath)));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resume = mapper.readValue(resumeJson, Map.class);

        List<String> skills = new ArrayList<>();
        if (resume.get("skills") != null) {
            skills.addAll((List<String>) resume.get("skills"));
        }
        if (resume.get("softSkills") != null) {
            skills.addAll((List<String>) resume.get("softSkills"));
        }

        return skills;
    }



    public Map<String, List<String>> getMissedSkills(List<String> skills, String jobTitle, String jobDescription, String company) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("skills", skills);
        body.put("job_title", jobTitle);
        body.put("job_description", jobDescription);
        body.put("company", company);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = "https://vectra-backend-ai.issaminu.com/suggest_skills";

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, List<String>> result = response.getBody();

            // Calculate the matching percentage
            int totalSkills = skills.size();
            int missedSkills = ((List<String>)result.get("technical_skills_missing")).size() + ((List<String>)result.get("soft_skills_missing")).size();
            double matchingPercentage = (double)totalSkills / (totalSkills + missedSkills) * 100;

            List<String> matchList = new ArrayList<>();
            matchList.add(String.valueOf(matchingPercentage));

            // Add the matching percentage to the result
            result.put("match", matchList);

            return result;
        } catch (HttpStatusCodeException e) {
            System.out.println("Error occurred while fetching missed skills: " + e.getMessage());
            return null;
        }
    }

    private List<String> enhanceExperience(List<Map<String, Object>> experiences) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("experience", experiences);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = "https://vectra-backend-ai.issaminu.com/enhance_experience";

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, List<String>> result = response.getBody();

            return result.get("descriptions");
        } catch (HttpStatusCodeException e) {
            System.out.println("Error occurred while enhancing experiences: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void enhanceResume(String username, Map<String, Object> enhancementDetails) throws IOException {
        // Retrieve the existing resume
        String dirPath = "./resumes";
        String filePath = dirPath + "/" + username + ".json";
        String resumeJson = new String(Files.readAllBytes(Paths.get(filePath)));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resume = mapper.readValue(resumeJson, Map.class);

        // Replace the skills with the final skills from the enhancement details
        resume.put("softSkills", enhancementDetails.get("soft_skills"));
        resume.put("skills", enhancementDetails.get("hard_skills"));

        // Get the experiences from the existing resume


        // Check if the "enhance" attribute in enhancementDetails is true
        if (Boolean.TRUE.equals(enhancementDetails.get("enhance"))) {
            List<Map<String, Object>> existingExperiences = (List<Map<String, Object>>) resume.get("experience");

            // Call the /enhance_experience endpoint with the existing experiences to get the enhanced descriptions
            List<String> enhancedDescriptions = enhanceExperience(existingExperiences);

            // Replace the descriptions in the existing experiences with the enhanced descriptions
            for (int i = 0; i < existingExperiences.size(); i++) {
                existingExperiences.get(i).put("description", enhancedDescriptions.get(i));
            }
        }

        // Convert the updated resume to JSON
        String newResumeJson = mapper.writeValueAsString(resume);

        // Generate the new filename
        String jobTitle = ((String) enhancementDetails.get("job_title")).replace(" ", "_");
        String companyName = ((String) enhancementDetails.get("company")).replace(" ", "_");
        String newUsername = username + "_" + jobTitle + "_" + companyName;

        // Save the new resume
        saveResume(newResumeJson, newUsername);
    }



}