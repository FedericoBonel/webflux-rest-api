package com.federicobonel.webfluxrestapi.repositories;

import com.federicobonel.webfluxrestapi.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
