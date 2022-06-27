package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class CustomerControllerTest {

    public static final String ID = "id1234";
    public static final String FIRST_NAME = "Pablo";
    public static final String LAST_NAME = "Escobar";
    public static final Integer NUMBER_OF_CUSTOMERS = 3;
    public static final String CUSTOMER_URL = CustomerController.CUSTOMER_URL + "/" + ID;

    Mono<Customer> customer;
    Flux<Customer> customers;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Customer customerInstance = new Customer();
        customerInstance.setId(ID);
        customerInstance.setFirstname(FIRST_NAME);
        customerInstance.setLastname(LAST_NAME);
        customer = Mono.just(customerInstance);

        customers = Flux.just(new Customer(), new Customer(), new Customer());

        webTestClient = WebTestClient.bindToController(customerController).build();
    }

    @Test
    void getAll() {
        given(customerService.getAll()).willReturn(customers);

        webTestClient.get()
                .uri(CustomerController.CUSTOMER_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class)
                .hasSize(NUMBER_OF_CUSTOMERS);
    }

    @Test
    void getById() {
        given(customerService.getById(ID)).willReturn(customer);

        webTestClient.get()
                .uri(CUSTOMER_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class);

    }

    @Test
    void createCustomer() {
        given(customerService.saveAll(any(Publisher.class))).willReturn(customers);

        webTestClient.post()
                .uri(CustomerController.CUSTOMER_URL)
                .body(customers, Customer.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void putById() {
        given(customerService.putById(anyString(), any(Mono.class))).willReturn(customer);

        webTestClient.put()
                .uri(CUSTOMER_URL)
                .body(customer, Customer.class)
                .exchange()
                .expectStatus().isOk();
    }
}