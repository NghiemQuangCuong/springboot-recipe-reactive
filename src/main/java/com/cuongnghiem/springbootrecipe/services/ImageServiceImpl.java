package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by cuongnghiem on 17/10/2021
 **/
@Slf4j
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final RecipeService recipeService;

    public ImageServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) throws IOException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new RuntimeException("Cannot find recipe id = " + recipeId);

        recipe.setImage(file.getBytes());
        recipeService.save(recipe);

        log.debug("Image Saved");
    }
}
