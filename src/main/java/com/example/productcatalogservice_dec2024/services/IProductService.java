package com.example.productcatalogservice_dec2024.services;

import com.example.productcatalogservice_dec2024.dtos.CreateRequestProductDto;
import com.example.productcatalogservice_dec2024.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product createProduct(CreateRequestProductDto createRequestProductDto);
}
