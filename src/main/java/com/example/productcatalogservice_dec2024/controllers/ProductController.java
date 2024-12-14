package com.example.productcatalogservice_dec2024.controllers;

import com.example.productcatalogservice_dec2024.dtos.*;
import com.example.productcatalogservice_dec2024.models.Product;
import com.example.productcatalogservice_dec2024.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> index() {
        return productService.getAllProducts().stream().map(this::from).toList();
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody CreateRequestProductDto createRequestProductDto) {
        Product product = productService.createProduct(createRequestProductDto);
        if (product == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(from(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> show(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Product product = productService.getProductById(id);
        if (product == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(from(product), HttpStatus.OK);
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }
}
