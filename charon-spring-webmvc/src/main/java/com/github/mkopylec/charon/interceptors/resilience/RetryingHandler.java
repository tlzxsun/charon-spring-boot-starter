package com.github.mkopylec.charon.interceptors.resilience;

import com.github.mkopylec.charon.interceptors.HttpRequest;
import com.github.mkopylec.charon.interceptors.HttpRequestExecution;
import com.github.mkopylec.charon.interceptors.HttpResponse;
import io.github.resilience4j.retry.RetryConfig;

import org.springframework.web.client.HttpClientErrorException;

import static io.github.resilience4j.retry.RetryConfig.custom;
import static java.time.Duration.ofMillis;

class RetryingHandler extends ResilienceHandler<RetryConfig> {

    RetryingHandler() {
        super(custom()
                .waitDuration(ofMillis(10))
                .ignoreExceptions(HttpClientErrorException.class)
                .build());
    }

    @Override
    public HttpResponse forward(HttpRequest request, HttpRequestExecution execution) {
        // TODO Implement retrying
        return null;
    }

    @Override
    public int getOrder() {
        return REQUEST_RETRYING_HANDLER_ORDER;
    }
}
