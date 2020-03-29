//package com.seepine.demo.gateway.server.handles;
//
//import com.seepine.demo.gateway.server.utils.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.HandlerFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
///**
// * @author Seepine
// * @date 2020/3/30 00:01
// */
//@Component
//public class HystrixFallbackHandle implements HandlerFunction<ServerResponse> {
//    @Override
//    @NonNull
//    public Mono<ServerResponse> handle(@NonNull ServerRequest serverRequest) {
//        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).bodyValue(Response.build(HttpStatus.SERVICE_UNAVAILABLE));
//    }
//}
