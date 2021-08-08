package com.annakhuseinova.springwebflux.service;

import com.annakhuseinova.springwebflux.SleepUtil;
import com.annakhuseinova.springwebflux.dto.MultiplyRequestDto;
import com.annakhuseinova.springwebflux.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public Response findSquare(int input){
        return new Response(input * input);
    }

    public List<Response> multiplicationTable(int input){
        return IntStream.rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("math-service processing :" + i))
                .mapToObj(item -> new Response(item * input))
                .collect(Collectors.toList());
    }
}
