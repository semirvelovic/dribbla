package com.dribbla.jobs.orderprocessjob;

import org.jboss.logging.Logger;

import com.dribbla.jobs.JobHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderProcessJob implements JobHandler {

    private final String JOB_NAME = "ORDER_PROCESS";

    private final ObjectMapper objectMapper;

    private static final Logger LOG = Logger.getLogger(OrderProcessJob.class);

    @Inject
    public OrderProcessJob(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String name() {
        return JOB_NAME;
    }

    @Override
    public void run(String payloadJson) {
        LOG.info("START ORDER");
        OrderDto orderDto;
        try {
            orderDto = objectMapper.readValue(payloadJson, OrderDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid order job payload", e);
        }
        LOG.info(orderDto.orderNumber);
    }
}
