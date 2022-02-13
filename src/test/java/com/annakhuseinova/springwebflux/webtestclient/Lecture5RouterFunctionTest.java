package com.annakhuseinova.springwebflux.webtestclient;

import com.annakhuseinova.springwebflux.config.RequestHandler;
import com.annakhuseinova.springwebflux.config.RouterConfig;
import com.annakhuseinova.springwebflux.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lecture5RouterFunctionTest {

    private WebTestClient webTestClient;
    @Autowired
    private RouterConfig config;
    @MockBean
    private RequestHandler requestHandler;
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeAll
    public void setClient(){
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    void test(){
        Mockito.when(requestHandler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new Response(225)));
        this.webTestClient
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> Assertions.assertThat(response.getOutput()).isEqualTo(225));
    }
}
