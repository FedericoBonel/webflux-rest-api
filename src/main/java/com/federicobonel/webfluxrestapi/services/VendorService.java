package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.model.Vendor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VendorService {

    Flux<Vendor> getAll();

    Mono<Vendor> getById(String id);

    Flux<Vendor> saveAll(Publisher<Vendor> vendors);

    Mono<Vendor> putById(String id, Mono<Vendor> vendor);

    Mono<Vendor> patchById(String id, Mono<Vendor> vendor);
}
