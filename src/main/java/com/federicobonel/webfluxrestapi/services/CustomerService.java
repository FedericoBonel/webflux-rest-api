package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.model.Customer;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<Customer> getAll();

    Mono<Customer> getById(String id);

    Flux<Customer> saveAll(Publisher<Customer> customers);

}
