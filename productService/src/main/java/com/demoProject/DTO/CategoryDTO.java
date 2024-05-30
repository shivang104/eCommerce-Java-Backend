package com.demoProject.DTO;

import com.demoProject.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CategoryDTO {
    private UUID uuid;
    private String name;
}
