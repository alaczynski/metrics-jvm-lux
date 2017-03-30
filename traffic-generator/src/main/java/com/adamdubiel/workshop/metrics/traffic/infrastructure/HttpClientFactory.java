package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

@Component
public class HttpClientFactory {

    public HttpClient client(int maxConns) {
        return httpClient(connectionManager(maxConns));
    }

//    public AsyncRestTemplate create() {
//        DefaultAsyncClientHttpRequestFactory requestFactory = asyncRequestFactory();
//        AsyncRestTemplate template = new AsyncRestTemplate(requestFactory, requestFactory);
//        return template;
//    }

    private HttpClient httpClient(PoolingHttpClientConnectionManager connectionManager) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setSocketTimeout(1500)
                .build();

        HttpClientBuilder clientBuilder = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig);

        return clientBuilder.build();
    }

    PoolingHttpClientConnectionManager connectionManager(int maxConns) {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(maxConns);
        manager.setDefaultMaxPerRoute(maxConns);

        return manager;
    }
}
