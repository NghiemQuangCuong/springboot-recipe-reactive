package com.cuongnghiem.springbootrecipe.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by cuongnghiem on 17/10/2021
 **/

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file) throws IOException;
}
