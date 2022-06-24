package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(CategoryController.CATEGORY_URL)
public class CategoryController {

    public static final String CATEGORY_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Flux<Category> getAll() {
        log.info("Getting all categories");

        return categoryService.getAll();
    }

    @GetMapping("/{categoryId}")
    public Mono<Category> getById(@PathVariable String categoryId) {
        log.info("Getting category by id " + categoryId);

        return categoryService.getById(categoryId);
    }

}
