package com.demoProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductCreationDTO {
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
}
