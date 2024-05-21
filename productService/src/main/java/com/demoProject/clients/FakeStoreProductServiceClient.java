package com.demoProject.clients;

import com.demoProject.DTO.FakeStoreProductCreationDTO;
import com.demoProject.DTO.FakeStoreProductDTO;
import com.demoProject.DTO.GenericProductDTO;
import com.demoProject.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductServiceClient implements ThirdPartyProductService{
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${fakestore.api.url}")
    private String fakeStoreApiUrl;

    @Value("${fakestore.api.paths.product}")
    private String fakeStoreProductsApiPath;

    private String specificProductRequestUrl;
    private String productRequestBaseUrl;

    public FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder,
                                         @Value("${fakestore.api.url}") String fakeStoreApiUrl,
                                         @Value("${fakestore.api.paths.product}") String fakeStoreProductsApiPath){
        this.restTemplateBuilder = restTemplateBuilder;
        this.productRequestBaseUrl = fakeStoreApiUrl + fakeStoreProductsApiPath;
        this.specificProductRequestUrl = fakeStoreApiUrl + fakeStoreProductsApiPath +" /{id}";
    }

    private FakeStoreProductCreationDTO convertGenericProductDTOtoCreationDTO(GenericProductDTO product){
        FakeStoreProductCreationDTO fakeStoreProductDTO = new FakeStoreProductCreationDTO();
        fakeStoreProductDTO.setImage(product.getImage());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setCategory(product.getCategory());
        return fakeStoreProductDTO;
    }

    @Override
    public FakeStoreProductDTO getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //make get call
        ResponseEntity<FakeStoreProductDTO> response =
                restTemplate.getForEntity(specificProductRequestUrl, FakeStoreProductDTO.class, id);


        FakeStoreProductDTO fakeStoreProductDTO = response.getBody();

        if(fakeStoreProductDTO == null){
            throw new NotFoundException("Product with id:" + id + "doesn't exist.");
        }

        return fakeStoreProductDTO;
    }

    @Override
    public FakeStoreProductDTO createProduct(GenericProductDTO product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductCreationDTO fakeStoreProductCreationDTO = convertGenericProductDTOtoCreationDTO(product);
        //make post call
        ResponseEntity<FakeStoreProductDTO> response =
                restTemplate.postForEntity(productRequestBaseUrl, fakeStoreProductCreationDTO ,FakeStoreProductDTO.class);

        return response.getBody();
    }

    @Override
    public List<FakeStoreProductDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //make get call
        ResponseEntity<FakeStoreProductDTO[]> response =
                restTemplate.getForEntity(productRequestBaseUrl, FakeStoreProductDTO[].class);

        return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public FakeStoreProductDTO deleteProduct(Long id) throws NotFoundException{
        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDTO.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDTO>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDTO.class);
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.execute(specificProductRequestUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        FakeStoreProductDTO fakeStoreProductDTO = response.getBody();

        if(fakeStoreProductDTO == null){
            throw new NotFoundException("Product with id:" + id + "doesn't exist.");
        }

        return fakeStoreProductDTO;
    }

    @Override
    public FakeStoreProductDTO updateProductById(Long id, GenericProductDTO product) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //make put call
        FakeStoreProductCreationDTO fakeStoreProductCreationDTO = convertGenericProductDTOtoCreationDTO(product);
        HttpEntity<FakeStoreProductCreationDTO> request = new HttpEntity<>(fakeStoreProductCreationDTO);
        ResponseEntity<FakeStoreProductDTO> response =
                restTemplate.exchange(specificProductRequestUrl, HttpMethod.PUT, request,FakeStoreProductDTO.class, id);

        FakeStoreProductDTO fakeStoreProductDTO = response.getBody();

        if(fakeStoreProductDTO == null){
            throw new NotFoundException("Product with id:" + id + "doesn't exist.");
        }

        return fakeStoreProductDTO;
    }
}
