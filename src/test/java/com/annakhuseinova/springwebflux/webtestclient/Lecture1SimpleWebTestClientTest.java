package com.annakhuseinova.springwebflux.webtestclient;

import com.annakhuseinova.springwebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class Lecture1SimpleWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void stepVerifierTest(){
        Flux<Response> responseFlux = this.webTestClient.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();

    }

    @Test
    public void fluentAssertionApiTest(){
        this.webTestClient.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(response -> assertThat(response.getOutput()).isEqualTo(25));
    }
}
