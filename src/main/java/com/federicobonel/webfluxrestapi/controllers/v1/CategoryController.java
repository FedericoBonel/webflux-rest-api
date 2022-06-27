package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> getAll() {
        log.info("Getting all categories");

        return categoryService.getAll();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> getById(@PathVariable String categoryId) {
        log.info("Getting category by id " + categoryId);

        return categoryService.getById(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createCategory(@RequestBody Publisher<Category> categoriesToSave) {
        log.info("Saving categories");

        return categoryService.saveAll(categoriesToSave).then();
    }


    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> putCategoryById(@PathVariable String categoryId, @RequestBody Mono<Category> category) {
        log.info("Replacing data for category  with id: " + categoryId);

        return categoryService.putById(categoryId, category);
    }
}
