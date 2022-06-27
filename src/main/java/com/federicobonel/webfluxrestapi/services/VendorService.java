package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.model.Vendor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VendorService {

    Flux<Vendor> getAll();

    Mono<Vendor> getById(String id);

}
