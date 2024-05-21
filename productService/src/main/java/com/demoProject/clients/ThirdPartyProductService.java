package com.demoProject.clients;

import com.demoProject.DTO.FakeStoreProductDTO;
import com.demoProject.DTO.GenericProductDTO;
import com.demoProject.exceptions.NotFoundException;

import java.util.List;

public interface ThirdPartyProductService {
    FakeStoreProductDTO getProductById(Long id) throws NotFoundException;
    FakeStoreProductDTO createProduct(GenericProductDTO product);
    List<FakeStoreProductDTO> getAllProducts();
    FakeStoreProductDTO deleteProduct(Long id) throws NotFoundException;
    FakeStoreProductDTO updateProductById(Long id, GenericProductDTO product) throws NotFoundException;
}
