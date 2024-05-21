package com.demoProject.controllers;

import com.demoProject.DTO.GenericProductDTO;
import com.demoProject.exceptions.NotFoundException;
import com.demoProject.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/")
public class ProductController {

    private ProductService productService;
    public ProductController(@Qualifier("fakeStoreProductServiceImpl") ProductService productService){
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<GenericProductDTO>> getAllProducts(){
        ResponseEntity<List<GenericProductDTO>> response = new ResponseEntity<>(
                productService.getAllProducts(),
                HttpStatus.OK
        );
        return response;
    }
    @GetMapping("{id}")
    public ResponseEntity<GenericProductDTO> getProductById(@PathVariable("id") Long id) throws NotFoundException {
        ResponseEntity<GenericProductDTO> response = new ResponseEntity<>(
                productService.getProductById(id),
                HttpStatus.OK
        );
        return response;
    }
    @PostMapping
    public ResponseEntity<GenericProductDTO> createProduct(@RequestBody GenericProductDTO product){
        ResponseEntity<GenericProductDTO> response = new ResponseEntity<>(
                productService.createProduct(product),
                HttpStatus.OK
        );
        return response;
    }
    @PutMapping("{id}")
    public ResponseEntity<GenericProductDTO> updateProductById( @PathVariable("id") Long id, @RequestBody GenericProductDTO product) throws NotFoundException {
        ResponseEntity<GenericProductDTO> response = new ResponseEntity<>(
                productService.updateProductById(id, product),
                HttpStatus.OK
        );
        return response;
    }
    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDTO> deleteProductById(@PathVariable("id") Long id) throws NotFoundException {
        ResponseEntity<GenericProductDTO> response = new ResponseEntity<>(
            productService.deleteProduct(id),
            HttpStatus.OK
        );
        return response;
    }
}
