package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageService;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveImageFile() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "some String".getBytes(StandardCharsets.UTF_8));
        Recipe recipe = new Recipe();
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        when(recipeService.getRecipeById(anyString())).thenReturn(recipe);

        imageService.saveImageFile("1L", multipartFile);

        verify(recipeService).save(argumentCaptor.capture());

        Recipe recipe1 = argumentCaptor.getValue();
        assertNotNull(recipe1.getImage());
    }
}