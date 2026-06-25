package com.dribbla.gateway.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record CreateJobRequest(
                String name,
                JsonNode payloadJson) {
}
