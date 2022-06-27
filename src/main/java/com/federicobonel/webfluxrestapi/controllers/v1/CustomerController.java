package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(CustomerController.CUSTOMER_URL)
public class CustomerController {

    public static final String CUSTOMER_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Customer> getAll() {
        log.info("Getting all customers");

        return customerService.getAll();
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Customer> getById(@PathVariable String customerId) {
        log.info("Getting customer by id: " + customerId);

        return customerService.getById(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createCustomer(@RequestBody Publisher<Customer> customersToSave) {
        log.info("Saving customers");

        return customerService.saveAll(customersToSave).then();
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Customer> putCustomer(@PathVariable String customerId, @RequestBody Mono<Customer> customer) {
        log.info("Replacing data for customer with id: " + customerId);

        return  customerService.putById(customerId, customer);
    }
}
