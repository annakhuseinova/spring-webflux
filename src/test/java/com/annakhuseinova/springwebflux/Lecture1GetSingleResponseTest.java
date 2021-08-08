package com.annakhuseinova.springwebflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class Lecture1GetSingleResponseTest extends BaseTest{

    @Autowired
    private WebClient webClient;
}
