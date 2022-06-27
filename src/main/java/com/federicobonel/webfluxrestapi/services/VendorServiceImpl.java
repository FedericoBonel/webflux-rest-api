package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.exceptions.CouldNotSave;
import com.federicobonel.webfluxrestapi.exceptions.ResourceNotFoundException;
import com.federicobonel.webfluxrestapi.model.Vendor;
import com.federicobonel.webfluxrestapi.repositories.VendorRepository;
import org.reactivestreams.Publisher;
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

    @Override
    public Flux<Vendor> saveAll(Publisher<Vendor> vendors) {
        return vendorRepository.saveAll(vendors)
                .switchIfEmpty(Flux.error(CouldNotSave::new));
    }

    @Override
    public Mono<Vendor> putById(String id, Mono<Vendor> vendor) {
        return vendor
                .map(vendorObject -> {
                    vendorObject.setId(id);
                    return vendorObject;
                }).flatMap(vendorRepository::save)
                .switchIfEmpty(Mono.error(CouldNotSave::new));
    }

    @Override
    public Mono<Vendor> patchById(String id, Mono<Vendor> vendor) {
        return vendorRepository.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException::new))
                .zipWith(vendor)
                .map(vendors -> {
                    Vendor saved = vendors.getT1();
                    Vendor newer = vendors.getT2();

                    if (saved.getName() == null || !saved.getName().equals(newer.getName()) && newer.getName() != null) {
                        saved.setName(newer.getName());
                    }

                    return saved;
                })
                .flatMap(vendorRepository::save)
                .switchIfEmpty(Mono.error(CouldNotSave::new));

    }
}
