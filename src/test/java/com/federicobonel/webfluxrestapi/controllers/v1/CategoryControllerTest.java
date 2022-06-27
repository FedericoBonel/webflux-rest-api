package com.federicobonel.webfluxrestapi.controllers.v1;

import com.federicobonel.webfluxrestapi.model.Category;
import com.federicobonel.webfluxrestapi.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class CategoryControllerTest {

    public static final String ID = "id12345";
    public static final String NAME = "id12345";
    public static final Integer NUMBER_OF_CATEGORIES = 3;
    public static final String CATEGORY_URL = CategoryController.CATEGORY_URL + "/" + ID;

    Category category;
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

        category = new Category();
        category.setId(ID);
        category.setName(NAME);

        categories = Flux.just(new Category(), new Category(), new Category());
    }

    @Test
    void getAll() {
        given(categoryService.getAll()).willReturn(categories);

        webTestClient.get()
                .uri(CategoryController.CATEGORY_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(NUMBER_OF_CATEGORIES);
    }

    @Test
    void getById() {
        given(categoryService.getById(ID)).willReturn(Mono.just(category));

        webTestClient.get()
                .uri(CATEGORY_URL)
                .exchange()
                .expectBody(Category.class);
    }
}