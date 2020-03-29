//package com.seepine.demo.gateway.server.configs;
//
//import com.seepine.demo.gateway.server.handles.HystrixFallbackHandle;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
///**
// * @author Seepine
// * @date 2020/3/29
// */
//@Configuration
//@AllArgsConstructor
//public class GatewayRoutesConfig {
//    private final HystrixFallbackHandle hystrixFallbackHandle;
//
//    @Bean
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route(RequestPredicates.path("/fallback")
//                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hystrixFallbackHandle);
//    }
//}
