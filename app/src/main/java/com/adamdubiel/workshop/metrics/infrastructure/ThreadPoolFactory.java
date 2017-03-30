package com.adamdubiel.workshop.metrics.infrastructure;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolFactory {

    public ExecutorService executorService(int threads) {
        return Executors.newFixedThreadPool(threads);
    }

}
