package com.demoProject.controllers;

import com.demoProject.DTO.ProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.model.Product;
import com.demoProject.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products/")
public class ProductController {

    private ProductService productService;
    public ProductController(@Qualifier("productServiceImpl") ProductService productService){
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        ResponseEntity<List<ProductDTO>> response = new ResponseEntity<>(
                productService.getAllProducts(),
                HttpStatus.OK
        );
        return response;
    }
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") UUID id) throws NotFoundException {
        ResponseEntity<ProductDTO> response = new ResponseEntity<>(
                productService.getProductById(id),
                HttpStatus.OK
        );
        return response;
    }
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product){
        ResponseEntity<ProductDTO> response = new ResponseEntity<>(
                productService.createProduct(product),
                HttpStatus.OK
        );
        return response;
    }
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable("id") UUID id, @RequestBody Product product) throws NotFoundException {
        ResponseEntity<ProductDTO> response = new ResponseEntity<>(
                productService.updateProductById(id, product),
                HttpStatus.OK
        );
        return response;
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable("id") UUID id) throws NotFoundException {
        ResponseEntity<ProductDTO> response = new ResponseEntity<>(
            productService.deleteProduct(id),
            HttpStatus.OK
        );
        return response;
    }
}
