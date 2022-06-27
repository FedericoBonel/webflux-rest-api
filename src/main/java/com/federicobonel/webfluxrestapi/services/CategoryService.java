package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.model.Category;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAll();

    Mono<Category> getById(String id);

    Flux<Category> saveAll(Publisher<Category> categories);

    Mono<Category> putById(String id, Mono<Category> category);

    Mono<Category> patchById(String id, Mono<Category> category);

}
