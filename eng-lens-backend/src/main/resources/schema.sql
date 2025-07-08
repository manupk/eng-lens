-- Team table
CREATE TABLE team (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Developer table
CREATE TABLE developer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    github_username VARCHAR(255),
    team_id BIGINT REFERENCES team(id)
);

-- Git Commit Activity table
CREATE TABLE git_commit_activity (
    id BIGSERIAL PRIMARY KEY,
    developer_id BIGINT REFERENCES developer(id),
    sha VARCHAR(64) NOT NULL,
    message TEXT,
    commit_date TIMESTAMP NOT NULL,
    repo_owner VARCHAR(255) NOT NULL,
    repo_name VARCHAR(255) NOT NULL
);

-- Git Pull Request Activity table
CREATE TABLE git_pull_request_activity (
    id BIGSERIAL PRIMARY KEY,
    developer_id BIGINT REFERENCES developer(id),
    pr_number INT NOT NULL,
    title TEXT,
    state VARCHAR(32),
    created_at TIMESTAMP NOT NULL,
    merged_at TIMESTAMP,
    repo_owner VARCHAR(255) NOT NULL,
    repo_name VARCHAR(255) NOT NULL
); 