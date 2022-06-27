package com.federicobonel.webfluxrestapi.controllers;

import com.federicobonel.webfluxrestapi.exceptions.CouldNotSave;
import com.federicobonel.webfluxrestapi.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ResourceNotFoundException.class})
    public Mono<ResponseEntity<Object>> notFoundException() {
        return Mono.just(new ResponseEntity<>("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({CouldNotSave.class})
    public Mono<ResponseEntity<Object>> couldNotSave() {
        return Mono.just(new ResponseEntity<>(
                "There was a problem while creating the objects",
                new HttpHeaders(),
                HttpStatus.CONFLICT));
    }
}
