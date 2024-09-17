package com.projects.productservice.dtos;

import com.projects.productservice.models.Product;
import lombok.Data;

import java.util.List;
@Data
public class ProductsResponseDto {
    private List<Product> productList;
    private Response response;
}
