CREATE TABLE jobs (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    payload_json JSONB NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    worker_id VARCHAR(36),
    started_at TIMESTAMP NULL,
    finished_at TIMESTAMP NULL,
    error_message TEXT NULL
);
