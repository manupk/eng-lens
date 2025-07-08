package com.englens.controller;

import com.englens.domain.Activity;
import com.englens.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        return activityService.getActivityById(id)
                .map(existing -> {
                    activity.setId(id);
                    return ResponseEntity.ok(activityService.saveActivity(activity));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        if (activityService.getActivityById(id).isPresent()) {
            activityService.deleteActivity(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 