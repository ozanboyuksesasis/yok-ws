package com.sesasis.donusum.yok.excepiton;

import lombok.Data;

@Data
public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(String message) {
        super(message);
    }
}
