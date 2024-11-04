package com.sesasis.donusum.yok.excepiton.fotograf;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ListEmptyException extends RuntimeException {

    public ListEmptyException(String message) {
        super(message);
    }
}
