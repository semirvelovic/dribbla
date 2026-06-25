package com.dribbla;

import com.dribbla.grpc.JobResponse;
import com.dribbla.jobs.JobHandler;
import io.quarkus.scheduler.Scheduled;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkerController {

    @Inject
    Instance<JobHandler> handlers;

    private static final Logger LOG = Logger.getLogger(WorkerController.class);

    @Inject
    private WorkerService workerService;

    @Scheduled(every = "1s")
    public void tryClaimJob() throws UnknownHostException {
        workerService.claimJob()
                .subscribe().with(
                        this::claimJob,
                        failure -> LOG.error("Failed to claim job", failure));
    }

    private void claimJob(JobResponse job) {
        Map<String, JobHandler> byName = handlers.stream()
                .collect(Collectors.toMap(JobHandler::name, Function.identity()));

        if (job.getId().isBlank()) {
            return;
        }

        LOG.infof("Claimed job %s", job.getName());
        JobHandler handler = byName.get(job.getName());

        if (handler == null) {
            LOG.warnf("No handler registered for job %s", job.getName());
            return;
        }

        try {
            handler.run(job.getPayloadJson());
            workerService.updateJobStatus(job.getId(), "SUCCESS");
        } catch (RuntimeException e) {
            LOG.errorf(e, "Failed to run job %s (%s)", job.getName(), job.getId());
            workerService.updateJobStatus(job.getId(), "FAILED");
        }
    }
}
