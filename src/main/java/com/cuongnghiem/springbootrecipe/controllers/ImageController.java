package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.exception.NotFoundException;
import com.cuongnghiem.springbootrecipe.services.ImageService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cuongnghiem on 17/10/2021
 **/

@Slf4j
@Controller
@RequestMapping("/recipe")
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/{recipeId}/image/upload")
    public String getUploadImageForm(@PathVariable String recipeId, Model model) {
        if (recipeService.getRecipeById(Long.valueOf(recipeId)) == null)
            throw new NotFoundException("Recipe not found, recipeId = " +recipeId);
        model.addAttribute("recipeId", Long.valueOf(recipeId));
        return "recipe/image/upload";
    }

    @PostMapping("/{recipeId}/image")
    public String saveImage(@PathVariable String recipeId,
                            @RequestParam("imageFile") MultipartFile file) throws IOException {
        imageService.saveImageFile(Long.valueOf(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/{recipeId}/imageFile")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        byte[] image = recipeService.getRecipeById(Long.valueOf(recipeId)).getImage();
        if (image == null)
            return ;
        response.setContentType("image/*");
        InputStream is = new ByteArrayInputStream(image);
        IOUtils.copy(is, response.getOutputStream());

        is.close();
        response.getOutputStream().close();
    }


}
