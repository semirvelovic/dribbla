package com.dribbla;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.dribbla.grpc.ClaimJobRequest;
import com.dribbla.grpc.JobResponse;
import com.dribbla.grpc.JobService;
import com.dribbla.grpc.JobStatusRequest;
import com.dribbla.grpc.JobStatusResponse;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkerService {

    @GrpcClient("job-service")
    JobService jobService;

    public Uni<JobResponse> claimJob() throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();
        ClaimJobRequest request = ClaimJobRequest.newBuilder().setWorkerId(hostname).build();
        return jobService.claimJob(request);
    }

    public void updateJobStatus(String jobId, String status) {
        JobStatusRequest request = JobStatusRequest.newBuilder().setJobId(jobId).setStatus(status).build();
        Uni<JobStatusResponse> response = jobService.updateJobStatus(request);

    }

}
