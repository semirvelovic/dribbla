package com.dribbla.jobs.emailjob;

import org.jboss.logging.Logger;

import com.dribbla.jobs.JobHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailJob implements JobHandler {

    private final ObjectMapper objectMapper;

    private static final Logger LOG = Logger.getLogger(EmailJob.class);

    @Inject
    public EmailJob(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String name() {
        return "DEMO_TASK";
    }

    @Override
    public void run(String payloadJson) {
        EmailDto emailDto;
        try {
            emailDto = objectMapper.readValue(payloadJson, EmailDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid email job payload", e);
        }
        LOG.info(emailDto.message);
    }
}
