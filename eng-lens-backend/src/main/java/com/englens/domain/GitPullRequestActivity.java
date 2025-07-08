package com.englens.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Data
public class GitPullRequestActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Column(name = "pr_number", nullable = false)
    private Integer prNumber;

    @Column(columnDefinition = "TEXT")
    private String title;

    private String state; // open/closed/merged

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "merged_at")
    private Instant mergedAt;

    @Column(name = "repo_owner", nullable = false)
    private String repoOwner;

    @Column(name = "repo_name", nullable = false)
    private String repoName;
} 