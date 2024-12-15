package com.example.productcatalogservice_dec2024.services;

import com.example.productcatalogservice_dec2024.dtos.CreateRequestProductDto;
import com.example.productcatalogservice_dec2024.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_dec2024.models.Category;
import com.example.productcatalogservice_dec2024.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {
    private static final String rootUri = "http://fakestoreapi.com";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.rootUri(rootUri).build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate
                .getForEntity("/products/" + id, FakeStoreProductDto.class, id);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return from(response.getBody());
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.rootUri(rootUri).build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate
                .getForEntity("/products", FakeStoreProductDto[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return List.of(response.getBody())
                    .stream()
                    .map(this::from)
                    .toList();
        }
        return List.of();
    }

    @Override
    public Product createProduct(CreateRequestProductDto createRequestProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.rootUri(rootUri).build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate
                .postForEntity(
                        "/products",
                        from(createRequestProductDto),
                        FakeStoreProductDto.class
                );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return from(response.getBody());
        }
        return null;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto from(CreateRequestProductDto createRequestProductDto) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(createRequestProductDto.getName());
        fakeStoreProductDto.setDescription(createRequestProductDto.getDescription());
        fakeStoreProductDto.setImage(createRequestProductDto.getImageUrl());
        fakeStoreProductDto.setPrice(createRequestProductDto.getPrice());
        fakeStoreProductDto.setCategory(createRequestProductDto.getCategory().getName());
        return fakeStoreProductDto;
    }
}
