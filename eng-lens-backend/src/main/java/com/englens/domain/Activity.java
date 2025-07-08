package com.englens.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;
    private String type; // commit, PR, review, jira
    private String source; // github, jira
    private Instant timestamp;
    @Column(columnDefinition = "TEXT")
    private String details;
} 