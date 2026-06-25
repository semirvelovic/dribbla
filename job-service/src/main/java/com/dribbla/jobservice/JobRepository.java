package com.dribbla.jobservice;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 * Klasse um Jobs aus der DB zu laden, speichern, etc.
 * 
 */
@ApplicationScoped
public class JobRepository implements PanacheRepositoryBase<JobEntity, String> {

    public List<JobEntity> listByStatus(String status) {
        return list("status", status);
    }

    public List<JobEntity> listAllOrdered() {
        return list("ORDER BY createdAt DESC");
    }

    public JobEntity loadUnclaimedJob() {
        return find("workerId is null ORDER BY createdAt ASC").firstResult();
    }
}
