package com.demoProject.services;

import com.demoProject.DTO.CategoryDTO;
import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Category;
import com.demoProject.model.Product;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO getCategoryById(UUID id) throws NotFoundException;
    List<CategoryDTO> getAllCategory();
    CategoryDTO addCategory( Category category);
    CategoryDTO updateCategory(UUID id, Category category) throws NotFoundException;
    CategoryDTO deleteCategory(UUID id) throws NotFoundException;
    List<ProductDTO> getAllProductsInCategory(UUID id) throws NotFoundException;
}
