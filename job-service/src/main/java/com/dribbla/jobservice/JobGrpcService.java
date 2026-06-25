package com.dribbla.jobservice;

import com.dribbla.grpc.ClaimJobRequest;
import com.dribbla.grpc.GetJobRequest;
import com.dribbla.grpc.JobResponse;
import com.dribbla.grpc.JobService;
import com.dribbla.grpc.JobStatusRequest;
import com.dribbla.grpc.JobStatusResponse;
import com.dribbla.grpc.ListJobsRequest;
import com.dribbla.grpc.ListJobsResponse;
import com.dribbla.grpc.SubmitJobRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//Implementiert generierte Klassen aus proto
@GrpcService
public class JobGrpcService implements JobService {

    @Inject
    JobRepository jobRepository;

    @Override
    @Transactional // DB Transaction
    @Blocking // Quarkus führt Methode in Worker-Thread aus, weil Hibernate blockiert
    public Uni<JobResponse> submitJob(SubmitJobRequest request) {
        JobEntity job = new JobEntity();
        job.id = UUID.randomUUID().toString();
        job.name = request.getName();
        job.payloadJson = request.getPayloadJson();
        job.status = "PENDING";
        job.createdAt = LocalDateTime.now();
        job.updatedAt = LocalDateTime.now();

        jobRepository.persist(job);

        return Uni.createFrom().item(JobMapper.toGrpc(job));
    }

    @Override
    @Transactional
    @Blocking
    public Uni<JobResponse> getJob(GetJobRequest request) {
        return Uni.createFrom().item(JobMapper.toGrpc(jobRepository.findById(request.getJobId())));
    }

    @Override
    @Transactional
    @Blocking
    public Uni<ListJobsResponse> listJobs(ListJobsRequest request) {
        List<JobEntity> jobs;

        jobs = jobRepository.listAll();
        ListJobsResponse response = ListJobsResponse
                .newBuilder()
                .addAllJobs(jobs.stream().map(JobMapper::toGrpc)
                        .toList())
                .build();
        return Uni.createFrom().item(response);
    }

    @Override
    @Transactional
    @Blocking
    public Uni<JobResponse> claimJob(ClaimJobRequest request) {
        String workerId = request.getWorkerId();
        JobEntity job = jobRepository.loadUnclaimedJob();
        if (job == null) {
            return Uni.createFrom().item(JobResponse.newBuilder().build());
        }
        job.workerId = workerId;
        job.startedAt = LocalDateTime.now();
        job.updatedAt = LocalDateTime.now();
        jobRepository.persist(job);

        return Uni.createFrom().item(JobMapper.toGrpc(job));
    }

    @Override
    @Transactional
    @Blocking
    public Uni<JobStatusResponse> updateJobStatus(JobStatusRequest request) {
        JobEntity job = jobRepository.findById(request.getJobId());
        job.status = request.getStatus();
        jobRepository.persist(job);
        return Uni.createFrom().item(JobStatusResponse.newBuilder().setStatus("ok").build());
    }

}
