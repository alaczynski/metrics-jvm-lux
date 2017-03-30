package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import com.adamdubiel.workshop.metrics.traffic.domain.VoteFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class TrafficGenerator implements AutoCloseable {

    private final URI baseuri;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    private final VoteFactory voteFactory;

    public TrafficGenerator(@Value("${baseuri}") URI baseuri, VoteFactory voteFactory) {
        this.baseuri = baseuri;
        this.voteFactory = voteFactory;
    }

    public void generateNormalTrafficAllEndpoints() {
        executor.submit(new ListTrafficGenerator(baseuri, 5, 10));
        executor.submit(new VoteTrafficGenerator(baseuri, 10, 0, 10, voteFactory));
        executor.submit(new AddRestaurantTrafficGenerator(baseuri, 0.1, 10));
    }

    public void generateAdds() {
        executor.submit(new AddRestaurantTrafficGenerator(baseuri, 10, 10));
    }

    public void generateLotOfAdds() {
        executor.submit(new AddRestaurantTrafficGenerator(baseuri, 100, 10));
    }

    public void generateLotOfVotes() {
        executor.submit(new VoteTrafficGenerator(baseuri, 100, 0, 100, voteFactory));
    }

    @Override
    public void close() throws Exception {
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }
}
