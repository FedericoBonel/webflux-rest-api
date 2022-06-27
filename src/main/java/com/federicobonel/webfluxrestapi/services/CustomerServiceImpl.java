package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.exceptions.CouldNotSave;
import com.federicobonel.webfluxrestapi.exceptions.ResourceNotFoundException;
import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.repositories.CustomerRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Flux<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> getById(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException::new));
    }

    @Override
    public Flux<Customer> saveAll(Publisher<Customer> customers) {
        return customerRepository.saveAll(customers)
                .switchIfEmpty(Flux.error(CouldNotSave::new));
    }

    @Override
    public Mono<Customer> putById(String id, Mono<Customer> customer) {
        return customer
                .map(customerObject -> {
                    customerObject.setId(id);
                    return customerObject;
                })
                .flatMap(customerRepository::save)
                .switchIfEmpty(Mono.error(CouldNotSave::new));
    }

    @Override
    public Mono<Customer> patchById(String id, Mono<Customer> customer) {
        return customerRepository.findById(id)
                .zipWith(customer)
                .map(customers -> {
                    Customer saved = customers.getT1();
                    Customer newer = customers.getT2();

                    if (saved.getFirstname() == null
                            || !saved.getFirstname().equals(newer.getFirstname()) && newer.getFirstname() != null) {
                        saved.setFirstname(newer.getFirstname());
                    }

                    if (saved.getLastname() == null
                            || !saved.getLastname().equals(newer.getLastname()) && newer.getLastname() != null) {
                        saved.setLastname(newer.getLastname());
                    }

                    return saved;
                })
                .flatMap(customerRepository::save)
                .switchIfEmpty(Mono.error(CouldNotSave::new));
    }
}
