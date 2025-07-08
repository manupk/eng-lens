package com.englens.service;

import com.englens.domain.GitCommitActivity;
import com.englens.domain.Team;
import com.englens.domain.Developer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GitHubIntegrationServiceTest {
    @Autowired
    private GitHubIntegrationService gitHubIntegrationService;

/*     @Test
    void fetchUserEvents_shouldReturnEventsOrError() {
        // Use a well-known public GitHub username for testing
        String username = "manupk";
        String result = gitHubIntegrationService.fetchUserEvents(username);
        assertThat(result).isNotNull();
        // Optionally, check for expected JSON structure or error message
        System.out.println(result);
    }

    @Test
    void fetchUserCommits_shouldReturnCommitsOrError() {
        String owner = "manupk";
        String repo = "yo-skills-core";
        String author = "manupk";
        List<GitHubIntegrationService.GitHubCommit> commits = gitHubIntegrationService.fetchUserCommits(owner, repo, author);
        assertThat(commits).isNotNull();
        System.out.println("User Commits: " + commits.size());
        if (!commits.isEmpty()) {
            GitHubIntegrationService.GitHubCommit c = commits.get(0);
            System.out.println("SHA: " + c.sha);
            if (c.commit != null) {
                System.out.println("Message: " + c.commit.message);
                if (c.commit.author != null) {
                    System.out.println("Author: " + c.commit.author.name);
                    System.out.println("Date: " + c.commit.author.date);
                }
            }
        }
    }

    @Test
    void fetchPullRequests_shouldReturnPRsOrError() {
        String owner = "manupk";
        String repo = "yo-skills-core";
        String result = gitHubIntegrationService.fetchPullRequests(owner, repo);
        assertThat(result).isNotNull();
        System.out.println("Pull Requests: " + result);
    }

    @Test
    void fetchCommitsSince_shouldReturnCommitsOrError() {
        String owner = "DevExp";
        String repo = "java-gha-demo";
        String since = "2024-05-01T00:00:00Z";
        List<GitCommitActivity> commits = gitHubIntegrationService.fetchCommitsSince(owner, repo, since);
        assertThat(commits).isNotNull();
        System.out.println("Commits since " + since + ": " + commits.size());
        if (!commits.isEmpty()) {
            GitCommitActivity c = commits.get(0);
            System.out.println("SHA: " + c.getSha());
            System.out.println("Message: " + c.getMessage());
            System.out.println("Date: " + c.getCommitDate());
        }
    }

*/


    @Test
    void insertCommitsForDev() {
        String owner = "DevExp";
        String repo = "java-gha-demo";
        String since = "2024-05-01T00:00:00Z";
        List<GitCommitActivity> commits = gitHubIntegrationService.fetchCommitsSince(owner, repo, since);
        assertThat(commits).isNotNull();
        System.out.println("Commits since " + since + ": " + commits.size());
        if (!commits.isEmpty()) {
            GitCommitActivity c = commits.get(0);
            System.out.println("SHA: " + c.getSha());
            System.out.println("Message: " + c.getMessage());
            System.out.println("Date: " + c.getCommitDate());
        }
        // Create and pass new Team and Developer objects
        Team team = new Team(1L);
        Developer developer = new Developer(1L, team);
        

        gitHubIntegrationService.saveCommitsForDeveloper(developer, commits);
    }
} 