package com.federicobonel.webfluxrestapi.repositories;

import com.federicobonel.webfluxrestapi.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
