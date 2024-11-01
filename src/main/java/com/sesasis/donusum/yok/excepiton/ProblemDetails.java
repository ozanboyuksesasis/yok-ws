package com.sesasis.donusum.yok.excepiton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetails {

    private String message;

    private LocalDateTime localDate;

    private String path;

    private HttpStatus errorCode;

}
