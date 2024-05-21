package com.demoProject.services;

import com.demoProject.DTO.GenericProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Product;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {
    GenericProductDTO getProductById(Long id) throws NotFoundException;
    GenericProductDTO createProduct(GenericProductDTO product);
    List<GenericProductDTO> getAllProducts();
    GenericProductDTO deleteProduct(Long id) throws NotFoundException;
    GenericProductDTO updateProductById(Long id, GenericProductDTO product) throws NotFoundException;
}
