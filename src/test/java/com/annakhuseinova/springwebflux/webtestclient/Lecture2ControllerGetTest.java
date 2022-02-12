package com.annakhuseinova.springwebflux.webtestclient;

import com.annakhuseinova.springwebflux.controller.ParamsController;
import com.annakhuseinova.springwebflux.controller.ReactiveMathController;
import com.annakhuseinova.springwebflux.dto.Response;
import com.annakhuseinova.springwebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lecture2ControllerGetTest {

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void singleResponseTest(){
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));
        this.webTestClient.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(response -> assertThat(response.getOutput()).isEqualTo(25));
    }

    @Test
    public void listResponseTest(){
        Flux<Response> flux = Flux.range(1, 3).map(Response::new);
        Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);
        this.webTestClient.get()
                .uri("/reactive-math/table/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

    @Test
    public void streamingTest(){
        Flux<Response> flux = Flux.range(1, 3).map(Response::new).delayElements(Duration.ofMillis(100));
        Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);
        this.webTestClient.get()
                .uri("/reactive-math/table/{number}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

    @Test
    public void paramsTest(){

        Map<String, Integer> map = Map.of("count", 10, "page", 20);
        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/job/search").query("count={count}&page={page}").build(map))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2).contains(10, 20);

    }
}
