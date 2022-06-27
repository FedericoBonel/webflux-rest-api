package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Vendor;
import com.federicobonel.webfluxrestapi.services.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(VendorController.VENDORS_URL)
public class VendorController {

    public static final String VENDORS_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public Flux<Vendor> getAll() {
        log.info("Getting all vendors");

        return vendorService.getAll();
    }

    @GetMapping("/{vendorId}")
    public Mono<Vendor> getById(@PathVariable String vendorId) {
        log.info("Getting vendor by id");

        return vendorService.getById(vendorId);
    }
}
