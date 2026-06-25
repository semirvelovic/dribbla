package com.dribbla.gateway;

import com.dribbla.gateway.dto.CreateJobRequest;
import com.dribbla.gateway.dto.JobDto;
import com.dribbla.gateway.mapper.JobMapper;
import com.dribbla.grpc.GetJobRequest;
import com.dribbla.grpc.JobService;
import com.dribbla.grpc.ListJobsRequest;
import com.dribbla.grpc.SubmitJobRequest;
import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/jobs")
public class JobResource {

    @GrpcClient("job-service")
    JobService jobService;

    @POST
    public Uni<JobDto> createJob(CreateJobRequest request) {
        var grpcRequest = SubmitJobRequest.newBuilder()
                .setName(request.name())
                .setPayloadJson(toPayloadJson(request.payloadJson()))
                .build();

        return jobService.submitJob(grpcRequest)
                .map(JobMapper::toDto);
    }

    @GET
    @Path("/{jobId}")
    public Uni<JobDto> getJob(@PathParam("jobId") String jobId) {
        var grpcRequest = GetJobRequest.newBuilder()
                .setJobId(jobId)
                .build();

        return jobService.getJob(grpcRequest)
                .map(JobMapper::toDto);
    }

    @GET
    public Uni<List<JobDto>> listJobs() {
        var grpcRequest = ListJobsRequest.newBuilder()
                .setStatus("")
                .build();

        return jobService.listJobs(grpcRequest)
                .map(response -> response.getJobsList()
                        .stream()
                        .map(JobMapper::toDto)
                        .toList());
    }

    private String toPayloadJson(JsonNode payloadJson) {
        if (payloadJson == null || payloadJson.isNull()) {
            return "{}";
        }

        return payloadJson.isTextual() ? payloadJson.asText() : payloadJson.toString();
    }
}
