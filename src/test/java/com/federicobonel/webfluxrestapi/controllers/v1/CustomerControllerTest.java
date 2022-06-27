package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class CustomerControllerTest {

    public static final String ID = "id1234";
    public static final String FIRST_NAME = "Pablo";
    public static final String LAST_NAME = "Escobar";
    public static final Integer NUMBER_OF_CUSTOMERS = 3;
    public static final String CUSTOMER_URL = CustomerController.CUSTOMER_URL + "/" + ID;

    Customer customer;
    Flux<Customer> customers;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        customers = Flux.just(new Customer(), new Customer(), new Customer());

        webTestClient = WebTestClient.bindToController(customerController).build();
    }

    @Test
    void getAll() {
        given(customerService.getAll()).willReturn(customers);

        webTestClient.get()
                .uri(CustomerController.CUSTOMER_URL)
                .exchange()
                .expectBodyList(Customer.class)
                .hasSize(NUMBER_OF_CUSTOMERS);
    }

    @Test
    void getById() {
        given(customerService.getById(ID)).willReturn(Mono.just(customer));

        webTestClient.get()
                .uri(CUSTOMER_URL)
                .exchange()
                .expectBody(Customer.class);

    }
}