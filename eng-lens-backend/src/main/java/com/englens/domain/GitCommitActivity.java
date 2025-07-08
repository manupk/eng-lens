package com.englens.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Data
public class GitCommitActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Column(nullable = false, length = 64)
    private String sha;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "commit_date", nullable = false)
    private Instant commitDate;

    @Column(name = "repo_owner", nullable = false)
    private String repoOwner;

    @Column(name = "repo_name", nullable = false)
    private String repoName;
} 