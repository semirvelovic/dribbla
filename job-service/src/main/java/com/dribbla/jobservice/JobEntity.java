package com.dribbla.jobservice;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class JobEntity {

    @Id
    public String id;

    @Column(nullable = false)
    public String name;

    @Column(name = "payload_json", nullable = false, columnDefinition = "TEXT")
    public String payloadJson;

    @Column(nullable = false)
    public String status;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    @Column(name = "started_at")
    public LocalDateTime startedAt;

    @Column(name = "finished_at")
    public LocalDateTime finishedAt;

    @Column(name = "error_message", columnDefinition = "TEXT")
    public String errorMessage;
}
