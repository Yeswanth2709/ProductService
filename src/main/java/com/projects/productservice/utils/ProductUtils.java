package com.projects.productservice.utils;

import com.projects.productservice.dtos.FakeStoreProductDto;
import com.projects.productservice.models.Category;
import com.projects.productservice.models.Product;

public class ProductUtils {
    public static Product convertDtotoProduct(FakeStoreProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
