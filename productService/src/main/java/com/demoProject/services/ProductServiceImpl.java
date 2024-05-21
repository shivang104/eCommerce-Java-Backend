package com.demoProject.services;

import com.demoProject.DTO.GenericProductDTO;
import com.demoProject.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{
    @Override
    public GenericProductDTO getProductById(Long id) throws NotFoundException {
        throw new NotFoundException("Product with id:" + id + "doesn't exist.");
    }

    @Override
    public GenericProductDTO createProduct(GenericProductDTO product) {
        return null;
    }

    @Override
    public List<GenericProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public GenericProductDTO deleteProduct(Long id)  throws NotFoundException {
        throw new NotFoundException("Product with id:" + id + "doesn't exist.");
    }

    @Override
    public GenericProductDTO updateProductById(Long id, GenericProductDTO product) throws NotFoundException {
        throw new NotFoundException("Product with id:" + id + "doesn't exist.");
    }
}
