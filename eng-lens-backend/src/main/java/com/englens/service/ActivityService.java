package com.englens.service;

import com.englens.data.ActivityRepository;
import com.englens.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    public List<Activity> getActivitiesByDeveloperIdAndDateRange(Long developerId, Instant start, Instant end) {
        // This method will require a custom query in the repository, to be implemented later
        return List.of();
    }
} 