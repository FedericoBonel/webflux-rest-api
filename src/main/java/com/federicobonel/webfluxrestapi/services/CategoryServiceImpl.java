package com.federicobonel.webfluxrestapi.services;

import com.federicobonel.webfluxrestapi.exceptions.CouldNotSave;
import com.federicobonel.webfluxrestapi.exceptions.ResourceNotFoundException;
import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Flux<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> getById(String id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException::new));
    }

    @Override
    public Flux<Category> saveAll(Publisher<Category> categories) {
        return categoryRepository.saveAll(categories)
                .switchIfEmpty(Flux.error(CouldNotSave::new));
    }

    @Override
    public Mono<Category> putById(String id, Mono<Category> category) {
        return category
                .map(categoryObject -> {
                    categoryObject.setId(id);
                    return categoryObject;
                })
                .flatMap(categoryRepository::save)
                .switchIfEmpty(Mono.error(CouldNotSave::new));
    }


}
