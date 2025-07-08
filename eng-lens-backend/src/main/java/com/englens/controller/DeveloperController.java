package com.englens.controller;

import com.englens.domain.Developer;
import com.englens.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;

    @GetMapping
    public List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable Long id) {
        return developerService.getDeveloperById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Developer createDeveloper(@RequestBody Developer developer) {
        return developerService.saveDeveloper(developer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable Long id, @RequestBody Developer developer) {
        return developerService.getDeveloperById(id)
                .map(existing -> {
                    developer.setId(id);
                    return ResponseEntity.ok(developerService.saveDeveloper(developer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        if (developerService.getDeveloperById(id).isPresent()) {
            developerService.deleteDeveloper(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 