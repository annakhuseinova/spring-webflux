package com.annakhuseinova.springwebflux.config;

import com.annakhuseinova.springwebflux.dto.Response;
import com.annakhuseinova.springwebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService reactiveMathService;

    // The difference between body(...) and bodyValue(...) is that bodyValue(...) is
    // designed for raw objects, and body(...) is designed for Publishers where we need to specify the type of emitted
    // values
    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = this.reactiveMathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

}
