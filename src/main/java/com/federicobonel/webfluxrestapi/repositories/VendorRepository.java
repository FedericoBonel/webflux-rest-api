package com.federicobonel.webfluxrestapi.repositories;

import com.federicobonel.webfluxrestapi.model.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
