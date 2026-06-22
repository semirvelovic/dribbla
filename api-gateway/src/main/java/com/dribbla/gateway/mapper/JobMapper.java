package com.dribbla.gateway.mapper;

import com.dribbla.gateway.dto.JobDto;
import com.dribbla.grpc.JobResponse;

public class JobMapper {

    public static JobDto toDto(JobResponse job) {
        return new JobDto(
                job.getId(),
                job.getName(),
                job.getPayloadJson(),
                job.getStatus(),
                job.getCreatedAt(),
                job.getUpdatedAt());
    }
}
