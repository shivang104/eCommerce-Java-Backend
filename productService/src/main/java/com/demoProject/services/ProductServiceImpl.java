package com.demoProject.services;

import com.demoProject.DTO.PriceDTO;
import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Product;
import com.demoProject.respositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
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
    public ProductDTO getProductById(UUID id) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new NotFoundException("");
        }
        Product product = productOptional.get();
        return convertProductToProductDTO(product);
    }

    @Override
    public ProductDTO createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return convertProductToProductDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product : products){
            productDTOList.add(convertProductToProductDTO(product));
        }
        return productDTOList;
    }

    @Override
    public ProductDTO deleteProduct(UUID id) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new NotFoundException("");
        }
        productRepository.delete(productOptional.get());
        return convertProductToProductDTO(productOptional.get());
    }

    @Override
    public ProductDTO updateProductById(UUID id, Product product) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new NotFoundException("");
        }
        Product productToUpdate = productOptional.get();
        productToUpdate.setTitle(product.getTitle());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setImage(product.getImage());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setPrice(product.getPrice());
        return convertProductToProductDTO(productRepository.save(productToUpdate));
    }
}
