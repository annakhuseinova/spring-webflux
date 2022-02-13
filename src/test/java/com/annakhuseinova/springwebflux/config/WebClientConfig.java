package com.annakhuseinova.springwebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .filter(this::sessionToken)
                .build();
    }

    @Bean
    public WebTestClient webTestClient(){
        return WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    // ClientRequest objects is immutable, you can only create a new one
    private Mono<ClientResponse> sessionToken(ClientRequest clientRequest, ExchangeFunction exchangeFunction){
        System.out.println("generating session token");
        ClientRequest clientRequestModified = ClientRequest.from(clientRequest)
                .headers(headers -> headers.setBearerAuth("some-lengthy-jwt")).build();
        return exchangeFunction.exchange(clientRequestModified);
    }

    private Mono<ClientResponse> sessionTokenAttribute(ClientRequest clientRequest, ExchangeFunction exchangeFunction){
        ClientRequest newRequest = clientRequest.attribute("auth").map(value -> value.equals("basic") ?
                withBasicAuth(clientRequest): withOauth(clientRequest)).orElse(clientRequest);
        return exchangeFunction.exchange(newRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request){
        return ClientRequest.from(request).headers(httpHeaders ->
                httpHeaders.setBasicAuth("username", "password")).build();
    }

    private ClientRequest withOauth(ClientRequest request){
        return ClientRequest.from(request).headers(httpHeaders ->
                httpHeaders.setBearerAuth("someToken")).build();
    }
}
