package com.cuongnghiem.springbootrecipe.controllers;

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
        try {
            if (recipeService.getRecipeById(Long.valueOf(recipeId)) == null)
                return "404";
            model.addAttribute("recipeId", Long.valueOf(recipeId));
            return "/recipe/image/upload";
        } catch (NumberFormatException exception) {
            return "404";
        }
    }

    @PostMapping("/{recipeId}/image")
    public String saveImage(@PathVariable String recipeId,
                            @RequestParam("imageFile") MultipartFile file) {
        try {
            imageService.saveImageFile(Long.valueOf(recipeId), file);
            return "redirect:/recipe/" + recipeId + "/show";
        } catch (NumberFormatException exception) {
            return "404";
        } catch (Exception exception) {
            return "404";
        }
    }

    @GetMapping("/{recipeId}/imageFile")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) {
        try {
            byte[] image = recipeService.getRecipeById(Long.valueOf(recipeId)).getImage();
            if (image == null)
                return ;
            response.setContentType("image/*");
            InputStream is = new ByteArrayInputStream(image);
            IOUtils.copy(is, response.getOutputStream());
        } catch (NumberFormatException exception) {
            log.debug("Cannot get recipe id = " + recipeId);
        } catch (IOException exception) {
            log.debug("IOException, cannot render image from database");
        } catch (RuntimeException exception) {
            log.debug(exception.getMessage());
            exception.printStackTrace();
        }
    }
}
