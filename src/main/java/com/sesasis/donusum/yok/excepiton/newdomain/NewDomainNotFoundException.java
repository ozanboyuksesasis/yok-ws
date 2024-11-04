package com.sesasis.donusum.yok.excepiton.newdomain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewDomainNotFoundException  extends RuntimeException {

    public NewDomainNotFoundException(String message) {
        super(message);
    }
}
