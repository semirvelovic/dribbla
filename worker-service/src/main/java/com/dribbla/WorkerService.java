package com.dribbla;

import com.dribbla.grpc.ClaimJobRequest;
import com.dribbla.grpc.JobResponse;
import com.dribbla.grpc.JobService;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkerService {

    @GrpcClient("job-service")
    JobService jobService;

    public Uni<JobResponse> claimJob() {
        ClaimJobRequest request = ClaimJobRequest.newBuilder().setWorkerId("1").build();
        return jobService.claimJob(request);
    }

}
