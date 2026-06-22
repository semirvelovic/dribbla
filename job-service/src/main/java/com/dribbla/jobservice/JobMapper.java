package com.dribbla.jobservice;

import java.time.LocalDateTime;

import com.dribbla.grpc.JobResponse;

/*
 * Klasse um Job zur GrpcResponse zu mappen 
 */
public class JobMapper {
    public static JobResponse toGrpc(JobEntity job) {
        return JobResponse.newBuilder()
                .setId(nullToEmpty(job.id))
                .setName(nullToEmpty(job.name))
                .setPayloadJson(nullToEmpty(job.payloadJson))
                .setStatus(nullToEmpty(job.status))
                .setCreatedAt(toString(job.createdAt))
                .setUpdatedAt(toString(job.updatedAt))
                .setStartedAt(toString(job.startedAt))
                .setFinishedAt(toString(job.finishedAt))
                .setErrorMessage(nullToEmpty(job.errorMessage))
                .build();
    }

    private static String toString(LocalDateTime value) {
        return value == null ? "" : value.toString();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
