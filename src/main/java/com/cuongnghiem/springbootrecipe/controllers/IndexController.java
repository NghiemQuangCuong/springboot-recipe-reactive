package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.model.Category;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import com.cuongnghiem.springbootrecipe.repositories.CategoryRepository;
import com.cuongnghiem.springbootrecipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by cuongnghiem on 23/09/2021
 **/
@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String getIndex(){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Italian");
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Pinch");

        System.out.println("Category found is " + categoryOptional.get().getDescription());
        System.out.println("UOM found is " + uomOptional.get().getDescription());
        return "index";
    }
}
