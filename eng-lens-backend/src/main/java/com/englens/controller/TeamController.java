package com.englens.controller;

import com.englens.domain.Team;
import com.englens.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.saveTeam(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return teamService.getTeamById(id)
                .map(existing -> {
                    team.setId(id);
                    return ResponseEntity.ok(teamService.saveTeam(team));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        if (teamService.getTeamById(id).isPresent()) {
            teamService.deleteTeam(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 