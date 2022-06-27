package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.model.Vendor;
import com.federicobonel.webfluxrestapi.services.CustomerService;
import com.federicobonel.webfluxrestapi.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

class VendorControllerTest {

    public static final String ID = "id1234";
    public static final String NAME = "Walmart St. Main Street 1234";
    public static final Integer NUMBER_OF_VENDORS = 3;
    public static final String VENDOR_URL = CustomerController.CUSTOMER_URL + "/" + ID;

    Vendor vendor;
    Flux<Vendor> vendors;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        vendors = Flux.just(new Vendor(), new Vendor(), new Vendor());

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }


    @Test
    void getAll() {
        given(vendorService.getAll()).willReturn(vendors);

        webTestClient.get()
                .uri(VendorController.VENDORS_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(NUMBER_OF_VENDORS);
    }

    @Test
    void getById() {
        given(vendorService.getById(ID)).willReturn(Mono.just(vendor));

        webTestClient.get()
                .uri(VENDOR_URL)
                .exchange()
                .expectBody(Vendor.class);
    }
}