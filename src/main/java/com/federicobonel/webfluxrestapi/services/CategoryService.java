package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAll();

    Mono<Category> getById(String id);

}
