package com.federicobonel.webfluxrestapi.datainitializers;


import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.model.Customer;
import com.federicobonel.webfluxrestapi.model.Vendor;
import com.federicobonel.webfluxrestapi.repositories.CategoryRepository;
import com.federicobonel.webfluxrestapi.repositories.CustomerRepository;
import com.federicobonel.webfluxrestapi.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public DataInitializer(CustomerRepository customerRepository, CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData() {
        customerRepository.count()
                .map(count -> {
                    if (count == 0) {
                        initializeCustomers();
                    }
                    return true;
                })
                .subscribe(customer -> log.info("Data initializer -> customers initialized!"));

        categoryRepository.count()
                .map(count -> {
                    if (count == 0) {
                        initializeCategories();
                    }
                    return Mono.empty();
                })
                .subscribe(category -> log.info("Data initializer -> categories initialized!"));

        vendorRepository.count()
                .map(count -> {
                    if (count == 0) {
                        initializeVendors();
                    }
                    return true;
                })
                .subscribe(vendor -> log.info("Data initializer -> vendors initialized!"));
    }

    private void initializeVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Carrefour");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Disco");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Naniwaya");

        Vendor vendor4 = new Vendor();
        vendor4.setName("Hanamasa");

        vendorRepository.saveAll(List.of(vendor1, vendor2, vendor3, vendor4)).subscribe();

    }

    private void initializeCustomers() {
        Customer customer = new Customer();
        customer.setFirstname("Name");
        customer.setLastname("Lastname");

        Customer customer1 = new Customer();
        customer1.setFirstname("Federico");
        customer1.setLastname("Bonel");

        Customer customer2 = new Customer();
        customer2.setFirstname("Haruna");
        customer2.setLastname("Takagi");

        Customer customer3 = new Customer();
        customer3.setFirstname("Antonio");
        customer3.setLastname("Tozzi");

        customerRepository.saveAll(List.of(customer, customer1, customer2, customer3)).subscribe();

    }

    private void initializeCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.saveAll(List.of(fruits, dried, fresh, exotic, nuts)).subscribe();

    }
}
