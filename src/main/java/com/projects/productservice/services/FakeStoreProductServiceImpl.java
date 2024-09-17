package com.projects.productservice.services;

import com.projects.productservice.dtos.FakeStoreProductDto;
import com.projects.productservice.exceptions.ProductNotFoundException;
import com.projects.productservice.models.Product;
import com.projects.productservice.utils.ProductUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class FakeStoreProductServiceImpl implements ProductService {
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDto productDto = restTemplate.getForEntity("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class).getBody();
        if(productDto==null){
            throw new ProductNotFoundException("Invalid Product Id");
        }
        return ProductUtils.convertDtotoProduct(productDto);

    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDto[] productDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto productDto:productDtos){
            products.add(ProductUtils.convertDtotoProduct(productDto));
        }
        return products;
    }
}
