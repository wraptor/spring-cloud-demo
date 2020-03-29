package com.seepine.demo.gateway.server.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * @param <T>
 * @author seepine
 * @date 2020/3/28 18:17
 */
@Data
public class Response<T> implements Serializable {
    private int code;
    private String msg;
    private T data;


    public static <T> Response<T> build(HttpStatus httpStatus) {
        return restResult(null, httpStatus.value(), httpStatus.name());
    }

    public static <T> Response<T> build(HttpStatus httpStatus, T data) {
        return restResult(data, httpStatus.value(), httpStatus.name());
    }

    private static <T> Response<T> restResult(T data, int code, String msg) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }
}