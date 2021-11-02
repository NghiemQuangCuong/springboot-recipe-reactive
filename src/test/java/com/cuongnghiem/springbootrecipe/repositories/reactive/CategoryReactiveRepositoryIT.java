package com.cuongnghiem.springbootrecipe.repositories.reactive;

import com.cuongnghiem.springbootrecipe.model.Category;
import com.cuongnghiem.springbootrecipe.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.save(Category.builder().id("1").description("1").build()).block();
        categoryReactiveRepository.save(Category.builder().id("2").description("2").build()).block();
        categoryReactiveRepository.save(Category.builder().id("3").description("3").build()).block();
    }

    @Test
    public void testReactive() {
        long count = categoryReactiveRepository.count().block();
        assertNotEquals(0L, count);

        categoryReactiveRepository.deleteById("1").block();
        categoryReactiveRepository.deleteById("2").block();
        categoryReactiveRepository.deleteById("3").block();
    }
}