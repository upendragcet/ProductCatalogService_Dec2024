package com.example.productcatalogservice_dec2024.dtos;

import com.example.productcatalogservice_dec2024.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequestProductDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private Double price;
    private Category category;
}
