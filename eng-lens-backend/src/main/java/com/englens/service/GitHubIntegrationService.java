package com.englens.service;

import com.englens.data.GitCommitActivityRepository;
import com.englens.domain.GitCommitActivity;
import com.englens.domain.Developer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Collections;
import java.util.List;
import java.time.Instant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GitHubIntegrationService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final GitCommitActivityRepository commitRepository;

    public GitHubIntegrationService(
            @Value("${github.token}") String githubToken,
            @Value("${github.api.baseurl}") String githubApiUrl,
            GitCommitActivityRepository commitRepository) {
        this.commitRepository = commitRepository;
        this.objectMapper = new ObjectMapper();
        this.webClient = WebClient.builder()
                .baseUrl(githubApiUrl)
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .build();
    }

    /**
     * Fetch public events for a given GitHub username (placeholder for future expansion).
     * @param username GitHub username
     * @return JSON string of events or error message
     */
    public String fetchUserEvents(String username) {
        try {
            return webClient.get()
                    .uri("/users/" + username + "/events/public")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Fetch commits for a given user and repository.
     * @param owner Repository owner
     * @param repo Repository name
     * @param author Commit author (GitHub username)
     * @return List of GitCommitActivity objects representing commits or empty list on error
     */
    public List<GitCommitActivity> fetchUserCommits(String owner, String repo, String author) {
        try {
            String json = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo + "/commits")
                        .queryParam("author", author)
                        .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return mapCommitsJsonToEntities(json, owner, repo, null);
        } catch (WebClientResponseException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Fetch pull requests for a given repository.
     * @param owner Repository owner
     * @param repo Repository name
     * @return JSON string of pull requests or error message
     */
    public String fetchPullRequests(String owner, String repo) {
        try {
            return webClient.get()
                    .uri("/repos/" + owner + "/" + repo + "/pulls")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Fetch all commits from a given repo since a given ISO 8601 date.
     * @param owner Repository owner
     * @param repo Repository name
     * @param sinceIsoDate ISO 8601 date string (e.g., 2024-05-01T00:00:00Z)
     * @return List of GitCommitActivity objects representing commits or empty list on error
     */
    public List<GitCommitActivity> fetchCommitsSince(String owner, String repo, String sinceIsoDate) {
        try {
            String json = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo + "/commits")
                        .queryParam("since", sinceIsoDate)
                        .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return mapCommitsJsonToEntities(json, owner, repo, null);
        } catch (WebClientResponseException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    /**
     * Saves a list of GitCommitActivity entities for a given developer.
     * Associates each commit with the specified developer and persists them in batch.
     *
     * @param developer The Developer entity to associate with the commits.
     * @param commits   The list of GitCommitActivity entities to save.
     */
    public void saveCommitsForDeveloper(Developer developer, List<GitCommitActivity> commits) {

        commits.forEach(commit -> commit.setDeveloper(developer));
        
        // Batch save all commits in one transaction
        commitRepository.saveAll(commits);
    }

    private List<GitCommitActivity> mapCommitsJsonToEntities(String json, String owner, String repo, Developer developer) {
        try {
            JsonNode root = objectMapper.readTree(json);
            List<GitCommitActivity> activities = new java.util.ArrayList<>();
            for (JsonNode node : root) {
                GitCommitActivity activity = new GitCommitActivity();
                activity.setSha(node.path("sha").asText());
                activity.setMessage(node.path("commit").path("message").asText());
                activity.setCommitDate(Instant.parse(node.path("commit").path("author").path("date").asText()));
                activity.setRepoOwner(owner);
                activity.setRepoName(repo);
                if (developer != null) activity.setDeveloper(developer);
                activities.add(activity);
            }
            return activities;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
} 