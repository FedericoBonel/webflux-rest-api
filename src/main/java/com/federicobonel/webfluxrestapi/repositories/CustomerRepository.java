package com.federicobonel.webfluxrestapi.repositories;

import com.federicobonel.webfluxrestapi.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
