package com.annakhuseinova.springwebflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture10Assignment extends BaseTest{

    private static final String FORMAT = "%d %s %d = %s";
    private static final int A = 10;

    @Autowired
    private WebClient webClient;

    @Test
    public void test(){
        Flux<String> flux = Flux.range(1, 5).flatMap(b -> Flux.just("+", "-", "*", "/").flatMap(op -> send(b, op)))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int b, String operation){
        return this.webClient
                .get()
                .uri("calculator/{a}/{b}", A, b)
                .headers(httpHeaders -> httpHeaders.set("OP", operation))
                .retrieve()
                .bodyToMono(String.class)
                .map(value -> String.format(FORMAT, A, operation, b, value));
    }
}
