package com.englens.data;

import com.englens.domain.GitCommitActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitCommitActivityRepository extends JpaRepository<GitCommitActivity, Long> {
} 