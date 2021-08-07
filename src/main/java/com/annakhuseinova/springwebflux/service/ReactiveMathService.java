package com.annakhuseinova.springwebflux.service;

import com.annakhuseinova.springwebflux.SleepUtil;
import com.annakhuseinova.springwebflux.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(()-> input* input).map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1, 10)
                .doOnNext(item -> SleepUtil.sleepSeconds(1))
                .doOnNext(item -> System.out.println("reactive-math-service processing : " + item))
                .map(i -> new Response(i *  input));
    }
}
