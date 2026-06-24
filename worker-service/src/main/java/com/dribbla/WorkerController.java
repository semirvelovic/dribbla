package com.dribbla;

import com.dribbla.grpc.JobResponse;

import io.quarkus.scheduler.Scheduled;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkerController {

    private static final Logger LOG = Logger.getLogger(WorkerController.class);

    @Inject
    private WorkerService workerService;

    @Scheduled(every = "1s")
    public void claimJob() {
        workerService.claimJob()
                .subscribe().with(
                        this::logClaimedJob,
                        failure -> LOG.error("Failed to claim job", failure));
    }

    private void logClaimedJob(JobResponse job) {
        if (job.getId().isBlank()) {
            LOG.debug("No unclaimed job available");
            return;
        }

        LOG.infof("Claimed job %s", job.getId());
    }
}
