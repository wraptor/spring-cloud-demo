package com.seepine.demo.gateway.server.controller;

import com.seepine.demo.gateway.server.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FallbackController
 *
 * @author seepine
 * @date 2020/3/28 18:19
 */
@RestController
public class FallbackController {
    @GetMapping("/fallback")
    public Response<String> fallback() {
        return Response.build(HttpStatus.SERVICE_UNAVAILABLE);
    }
}