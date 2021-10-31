package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.ImageService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @InjectMocks
    ImageController imageController;
    @Mock
    RecipeService recipeService;
    @Mock
    ImageService imageService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getUploadImageForm() throws Exception {

        when(recipeService.getRecipeById(anyString())).thenReturn(new Recipe());

        mockMvc.perform(get("/recipe/1L/image/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/image/upload"))
                .andExpect(model().attributeExists("recipeId"));
    }

    @Test
    void saveImage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile",
                "testing.txt",
                "plaint/text", "Some Random Test".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/recipe/1L/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1L/show"));

        verify(imageService).saveImageFile("1L", multipartFile);
    }

    @Test
    void renderImageFromDB() throws Exception {
        Recipe recipe = new Recipe();
        byte[] image = new byte[15];
        for (byte i = 0; i < 15; i++)
            image[i] = i;
        recipe.setImage(image);
        when(recipeService.getRecipeById(anyString())).thenReturn(recipe);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1L/imageFile"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("image/*", response.getContentType());
        byte[] responseImage = response.getContentAsByteArray();
        for (byte i = 0; i < responseImage.length; i++) {
            assertEquals(image[i], responseImage[i]);
        }
    }
}