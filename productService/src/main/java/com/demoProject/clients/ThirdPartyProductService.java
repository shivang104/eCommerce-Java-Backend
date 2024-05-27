package com.demoProject.clients;

import com.demoProject.DTO.FakeStoreProductDTO;
import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;

import java.util.List;

public interface ThirdPartyProductService {
    FakeStoreProductDTO getProductById(Long id) throws NotFoundException;
    FakeStoreProductDTO createProduct(ProductDTO product);
    List<FakeStoreProductDTO> getAllProducts();
    FakeStoreProductDTO deleteProduct(Long id) throws NotFoundException;
    FakeStoreProductDTO updateProductById(Long id, ProductDTO product) throws NotFoundException;
}
