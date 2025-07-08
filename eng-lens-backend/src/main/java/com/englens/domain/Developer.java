package com.englens.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "github_username")
    private String githubUsername;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Developer(Long id,Team team) {
        this.id = id;
        this.team = team;
    }

    public Developer() {

    }
    
}