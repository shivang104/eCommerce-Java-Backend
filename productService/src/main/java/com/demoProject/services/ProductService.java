package com.demoProject.services;

import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO getProductById(UUID id) throws NotFoundException;
    ProductDTO createProduct(Product product);
    List<ProductDTO> getAllProducts();
    ProductDTO deleteProduct(UUID id) throws NotFoundException;
    ProductDTO updateProductById(UUID id, Product product) throws NotFoundException;
}
