package com.github.mkopylec.charon;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

public class Main {

    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().ringBufferSizeInClosedState(1).build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("c1", circuitBreakerConfig);
        ServerWebExchange exchange;
        WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector())
                .filter((request, next) -> {
                    System.out.println("start 1");
                    return next.exchange(request)
                            .transform(CircuitBreakerOperator.of(circuitBreaker))
                            .doOnSuccess(clientResponse -> System.out.println("end 1"));
                })
                .filter((request, next) -> {
                    System.out.println("start 2");
                    return next.exchange(request)
                            .doOnSuccess(clientResponse -> System.out.println("end 2"));
                })
                .filter((request, next) -> {
                    System.out.println("start 3");
                    ClientRequest.Builder builder = ClientRequest.from(request).url(URI.create("http://www.meteo.pl/aaaa"));
                    return next.exchange(builder.build())
                            .doOnSuccess(clientResponse -> System.out.println("end 3"));
                })
                .build()
                .post()
                .uri("http://www.meteo.pl/swswsws")
                .exchange()
                .map(clientResponse -> {
                    return clientResponse;
                })

//                .bodyToMono(String.class)
//                .subscribeOn(Schedulers.fromExecutorService(new ThreadPoolTaskExecutor().getThreadPoolExecutor()))
//                .doOnError(Exception.class, e -> e.printStackTrace())
//                .subscribe();
                .block();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}