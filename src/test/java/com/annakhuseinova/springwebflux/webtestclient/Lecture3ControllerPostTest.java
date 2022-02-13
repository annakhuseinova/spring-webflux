package com.annakhuseinova.springwebflux.webtestclient;

import com.annakhuseinova.springwebflux.controller.ReactiveMathController;
import com.annakhuseinova.springwebflux.dto.MultiplyRequestDto;
import com.annakhuseinova.springwebflux.dto.Response;
import com.annakhuseinova.springwebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lecture3ControllerPostTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void postTest(){
        Mockito.when(reactiveMathService.multiplyPost(Mockito.any())).thenReturn(Mono.just(new Response(1)));
        this.webTestClient
                .post()
                .uri("/reactive-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .headers(httpHeaders -> httpHeaders.set("somekey", "somevalue"))
                .bodyValue(new MultiplyRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
