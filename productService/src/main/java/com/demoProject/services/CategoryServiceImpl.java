package com.demoProject.services;

import com.demoProject.DTO.CategoryDTO;
import com.demoProject.DTO.PriceDTO;
import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Category;
import com.demoProject.model.Product;
import com.demoProject.respositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    private CategoryDTO convertCategoryToCategoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setUuid(category.getUuid());
        return categoryDTO;
    }
    private ProductDTO convertProductToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getUuid());
        productDTO.setPrice(new PriceDTO(product.getPrice().getUuid(), product.getPrice().getCurrency(),product.getPrice().getPrice()));
        productDTO.getPrice().setId(product.getPrice().getUuid());
        productDTO.setTitle(product.getTitle());
        productDTO.setCategory(product.getCategory().getName());
        productDTO.setCategoryId(product.getCategory().getUuid());
        productDTO.setDescription(product.getDescription());
        productDTO.setImage(product.getImage());
        return productDTO;
    }
    @Override
    public CategoryDTO getCategoryById(UUID id) throws NotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new NotFoundException("");
        }
        return convertCategoryToCategoryDTO(category.get());
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(Category category : categories){
            categoryDTOList.add(convertCategoryToCategoryDTO(category));
        }
        return categoryDTOList;
    }

    @Override
    public CategoryDTO addCategory(Category category) {
        return convertCategoryToCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(UUID id, Category category) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            throw new NotFoundException("");
        }
        Category categoryToUpdate = categoryOptional.get();
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setProducts(category.getProducts());
        return convertCategoryToCategoryDTO(categoryRepository.save(categoryToUpdate));
    }

    @Override
    public CategoryDTO deleteCategory(UUID id) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            throw new NotFoundException("");
        }
        categoryRepository.delete(categoryOptional.get());
        return convertCategoryToCategoryDTO(categoryOptional.get());
    }

    @Override
    public List<ProductDTO> getAllProductsInCategory(UUID id) throws NotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new NotFoundException("");
        }
        List<Product> products = category.get().getProducts();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product : products){
            productDTOList.add(convertProductToProductDTO(product));
        }
        return productDTOList;
    }
}
