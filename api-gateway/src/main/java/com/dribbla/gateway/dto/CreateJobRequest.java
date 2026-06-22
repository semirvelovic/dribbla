package com.dribbla.gateway.dto;

public record CreateJobRequest(
        String name,
        String payloadJson) {
}
