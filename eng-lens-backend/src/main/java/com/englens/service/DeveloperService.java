package com.englens.service;

import com.englens.data.DeveloperRepository;
import com.englens.domain.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService {
    @Autowired
    private DeveloperRepository developerRepository;

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Optional<Developer> getDeveloperById(Long id) {
        return developerRepository.findById(id);
    }

    public Developer saveDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    public void deleteDeveloper(Long id) {
        developerRepository.deleteById(id);
    }
} 