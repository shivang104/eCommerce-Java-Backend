package com.demoProject.DTO;

import com.demoProject.model.Price;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDTO {
    private UUID id;
    private String title;
    private PriceDTO price;
    private UUID categoryId;
    private String category;
    private String description;
    private String image;
}