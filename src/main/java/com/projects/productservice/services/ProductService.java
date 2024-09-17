package com.projects.productservice.services;

import com.projects.productservice.exceptions.ProductNotFoundException;
import com.projects.productservice.models.Product;

import java.util.List;

public interface ProductService {
    public Product getProductById(long productId) throws ProductNotFoundException;
    public List<Product> getAllProducts();
}
