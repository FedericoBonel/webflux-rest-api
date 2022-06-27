package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Flux<Customer> getAll() {
        log.info("Getting all customers");

        return customerService.getAll();
    }

    @GetMapping("/{customerId}")
    public Mono<Customer> getById(@PathVariable String customerId) {
        log.info("Getting customer by id: " + customerId);

        return customerService.getById(customerId);
    }
}
