package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.exceptions.ResourceNotFoundException;
import com.federicobonel.webfluxrestapi.model.Vendor;
import com.federicobonel.webfluxrestapi.repositories.VendorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Flux<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @Override
    public Mono<Vendor> getById(String id) {
        return vendorRepository.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException::new));
    }
}
