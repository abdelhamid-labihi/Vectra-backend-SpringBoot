package com.vectra.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filePath; // path to the resume file

    public String getFilePath() {
        return filePath;
    }

    public Long getId() {
        return id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}