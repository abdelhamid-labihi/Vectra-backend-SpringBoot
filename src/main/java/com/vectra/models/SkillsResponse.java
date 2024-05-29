package com.vectra.models;

import java.util.List;

public class SkillsResponse {
    private List<String> technical_skills;
    private List<String> soft_skills;

    // Getters and setters
    public List<String> getTechnical_skills() {
        return technical_skills;
    }

    public void setTechnical_skills(List<String> technical_skills) {
        this.technical_skills = technical_skills;
    }

    public List<String> getSoft_skills() {
        return soft_skills;
    }

    public void setSoft_skills(List<String> soft_skills) {
        this.soft_skills = soft_skills;
    }
}