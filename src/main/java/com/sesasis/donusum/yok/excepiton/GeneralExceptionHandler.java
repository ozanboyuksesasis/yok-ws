package com.sesasis.donusum.yok.excepiton;

import com.sesasis.donusum.yok.excepiton.fotograf.ListEmptyException;
import com.sesasis.donusum.yok.excepiton.newdomain.NewDomainNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GeneralExceptionHandler {


    @ExceptionHandler(ListEmptyException.class)
    public ResponseEntity<ProblemDetails> handleListEmptyException(ListEmptyException e, WebRequest request) {
        ProblemDetails details = new ProblemDetails();
        details.setMessage(e.getMessage());
        details.setLocalDate(LocalDateTime.now());
        details.setPath(request.getDescription(false));
        details.setErrorCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NewDomainNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleNewDomainNotFoundException(NewDomainNotFoundException e, WebRequest request) {
        ProblemDetails details = new ProblemDetails();
        details.setMessage(e.getMessage());
        details.setLocalDate(LocalDateTime.now());
        details.setPath(request.getDescription(false));
        details.setErrorCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }



}
