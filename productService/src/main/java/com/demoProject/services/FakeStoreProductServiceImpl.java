package com.demoProject.services;

import com.demoProject.DTO.FakeStoreProductDTO;
import com.demoProject.DTO.GenericProductDTO;
import com.demoProject.clients.FakeStoreProductServiceClient;
import com.demoProject.clients.ThirdPartyProductService;
import com.demoProject.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductServiceImpl")
public class FakeStoreProductServiceImpl implements ProductService{
    private ThirdPartyProductService thirdPartyProductService;

    private GenericProductDTO convertFakeStoreProductToGenericProduct(FakeStoreProductDTO fakeStoreProductDTO){
        GenericProductDTO product = new GenericProductDTO();
        product.setId(fakeStoreProductDTO.getId());
        product.setImage(fakeStoreProductDTO.getImage());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setCategory(fakeStoreProductDTO.getCategory());
        return product;
    }

    public FakeStoreProductServiceImpl(ThirdPartyProductService thirdPartyProductService){
        this.thirdPartyProductService = thirdPartyProductService;
    }

    @Override
    public List<GenericProductDTO> getAllProducts() {
        List<GenericProductDTO> genericProductDTOList = new ArrayList<>();
        List<FakeStoreProductDTO> fakeStoreProductDTOList = thirdPartyProductService.getAllProducts();
        for(FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOList){
            genericProductDTOList.add(convertFakeStoreProductToGenericProduct(fakeStoreProductDTO));
        }
        return genericProductDTOList;
    }

    @Override
    public GenericProductDTO getProductById(Long id) throws NotFoundException{
        return convertFakeStoreProductToGenericProduct(thirdPartyProductService.getProductById(id));
    }

    @Override
    public GenericProductDTO createProduct(GenericProductDTO product) {
        return convertFakeStoreProductToGenericProduct(thirdPartyProductService.createProduct(product));
    }

    @Override
    public GenericProductDTO deleteProduct(Long id) throws NotFoundException{
        return convertFakeStoreProductToGenericProduct(thirdPartyProductService.deleteProduct(id));
    }

    @Override
    public GenericProductDTO updateProductById(Long id, GenericProductDTO product) throws NotFoundException {
         return convertFakeStoreProductToGenericProduct(thirdPartyProductService.updateProductById(id, product));
    }
}
