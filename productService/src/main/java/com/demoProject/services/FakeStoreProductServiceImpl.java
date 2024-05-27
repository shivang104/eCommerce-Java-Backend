//package com.demoProject.services;
//
//import com.demoProject.DTO.FakeStoreProductDTO;
//import com.demoProject.DTO.ProductDTO;
//import com.demoProject.clients.ThirdPartyProductService;
//import com.demoProject.exceptions.NotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service("fakeStoreProductServiceImpl")
//public class FakeStoreProductServiceImpl implements ProductService{
//    private ThirdPartyProductService thirdPartyProductService;
//
//    private ProductDTO convertFakeStoreProductToGenericProduct(FakeStoreProductDTO fakeStoreProductDTO){
//        ProductDTO product = new ProductDTO();
//        product.setId(fakeStoreProductDTO.getId());
//        product.setImage(fakeStoreProductDTO.getImage());
//        product.setDescription(fakeStoreProductDTO.getDescription());
//        product.setTitle(fakeStoreProductDTO.getTitle());
//        product.setPrice(fakeStoreProductDTO.getPrice());
//        product.setCategory(fakeStoreProductDTO.getCategory());
//        return product;
//    }
//
//    public FakeStoreProductServiceImpl(ThirdPartyProductService thirdPartyProductService){
//        this.thirdPartyProductService = thirdPartyProductService;
//    }
//
//    @Override
//    public List<ProductDTO> getAllProducts() {
//        List<ProductDTO> productDTOList = new ArrayList<>();
//        List<FakeStoreProductDTO> fakeStoreProductDTOList = thirdPartyProductService.getAllProducts();
//        for(FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOList){
//            productDTOList.add(convertFakeStoreProductToGenericProduct(fakeStoreProductDTO));
//        }
//        return productDTOList;
//    }
//
//    @Override
//    public ProductDTO getProductById(Long id) throws NotFoundException{
//        return convertFakeStoreProductToGenericProduct(thirdPartyProductService.getProductById(id));
//    }
//
//    @Override
//    public ProductDTO createProduct(ProductDTO product) {
//        return convertFakeStoreProductToGenericProduct(thirdPartyProductService.createProduct(product));
//    }
//
//    @Override
//    public ProductDTO deleteProduct(Long id) throws NotFoundException{
//        return convertFakeStoreProductToGenericProduct(thirdPartyProductService.deleteProduct(id));
//    }
//
//    @Override
//    public ProductDTO updateProductById(Long id, ProductDTO product) throws NotFoundException {
//         return convertFakeStoreProductToGenericProduct(thirdPartyProductService.updateProductById(id, product));
//    }
//}
