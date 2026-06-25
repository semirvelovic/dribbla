package com.dribbla.jobs;

public interface JobHandler {
    String name();

    void run(String payloadJson);
}