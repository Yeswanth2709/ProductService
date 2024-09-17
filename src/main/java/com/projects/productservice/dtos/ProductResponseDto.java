package com.projects.productservice.dtos;

import com.projects.productservice.models.Product;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Product product;
    private Response response;
}
