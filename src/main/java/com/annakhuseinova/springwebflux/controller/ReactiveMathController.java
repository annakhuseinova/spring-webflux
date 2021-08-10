package com.annakhuseinova.springwebflux.controller;


import com.annakhuseinova.springwebflux.dto.MultiplyRequestDto;
import com.annakhuseinova.springwebflux.dto.Response;
import com.annakhuseinova.springwebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {

    private final ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return this.reactiveMathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return this.reactiveMathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input){
        return this.reactiveMathService.multiplicationTable(input);
    }

    // When we specify Mono as request body type, Spring Boot will recognize that this request should be read
    // in an non-blocking way (will be taken to work only after the request read is complete)
    @PostMapping("multiply")
    public Mono<Response> multiplyPost(@RequestBody Mono<MultiplyRequestDto> requestDtoMono){
        return this.reactiveMathService.multiplyPost(requestDtoMono);
    }
}
