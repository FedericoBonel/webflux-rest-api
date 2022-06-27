package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class CategoryControllerTest {

    public static final String ID = "id12345";
    public static final String NAME = "id12345";
    public static final Integer NUMBER_OF_CATEGORIES = 3;
    public static final String CATEGORY_URL = CategoryController.CATEGORY_URL + "/" + ID;

    Mono<Category> category;
    Flux<Category> categories;

    WebTestClient webTestClient;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        webTestClient = WebTestClient.bindToController(categoryController).build();

        Category categoryInstance = new Category();
        categoryInstance.setId(ID);
        categoryInstance.setName(NAME);

        category = Mono.just(categoryInstance);

        categories = Flux.just(new Category(), new Category(), new Category());
    }

    @Test
    void getAll() {
        given(categoryService.getAll()).willReturn(categories);

        webTestClient.get()
                .uri(CategoryController.CATEGORY_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(NUMBER_OF_CATEGORIES);
    }

    @Test
    void getById() {
        given(categoryService.getById(ID)).willReturn(category);

        webTestClient.get()
                .uri(CATEGORY_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class);
    }

    @Test
    void createCategory() {
        given(categoryService.saveAll(any(Publisher.class))).willReturn(categories);

        webTestClient.post()
                .uri(CategoryController.CATEGORY_URL)
                .body(categories, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void putCategoryById() {
        given(categoryService.putById(anyString(), any(Mono.class))).willReturn(category);

        webTestClient.put()
                .uri(CATEGORY_URL)
                .body(category, Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void patchCategoryById() {
        given(categoryService.patchById(anyString(), any(Mono.class))).willReturn(category);

        webTestClient.patch()
                .uri(CATEGORY_URL)
                .body(category, Category.class)
                .exchange()
                .expectStatus().isOk();
    }
}