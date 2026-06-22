package com.dribbla.gateway.dto;

public record JobDto(
        String id,
        String name,
        String payload_json,
        String status,
        String created_at,
        String updated_at) {
}
