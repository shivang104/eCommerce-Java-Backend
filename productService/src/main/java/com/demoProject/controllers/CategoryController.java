package com.demoProject.controllers;

import com.demoProject.DTO.CategoryDTO;
import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Category;
import com.demoProject.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category/")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") UUID id) throws NotFoundException {
        ResponseEntity<CategoryDTO> responseEntity = new ResponseEntity<>(
                categoryService.getCategoryById(id),
                HttpStatus.OK
        );
        return responseEntity;
    }

    @GetMapping
    ResponseEntity<List<CategoryDTO>> getAllCategory(){
        ResponseEntity<List<CategoryDTO>> response = new ResponseEntity<>(
          categoryService.getAllCategory(),
          HttpStatus.OK
        );
        return response;
    }

    @PostMapping
    ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category){
        ResponseEntity<CategoryDTO> response = new ResponseEntity<>(
          categoryService.addCategory(category),
          HttpStatus.OK
        );
        return response;
    }

    @PutMapping("{id}")
    ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") UUID id, @RequestBody Category category) throws NotFoundException {
        ResponseEntity<CategoryDTO> response = new ResponseEntity<>(
                categoryService.updateCategory(id, category),
                HttpStatus.OK
        );
        return response;
    }

    @DeleteMapping("{id}")
    ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("id") UUID id) throws NotFoundException {
        ResponseEntity<CategoryDTO> response = new ResponseEntity<>(
                categoryService.deleteCategory(id),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping("getProducts/{id}")
    ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("id") UUID id) throws NotFoundException {
        ResponseEntity<List<ProductDTO>> response = new ResponseEntity<>(
                categoryService.getAllProductsInCategory(id),
                HttpStatus.OK
        );
        return response;
    }
}
