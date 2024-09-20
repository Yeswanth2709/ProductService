package com.projects.productservice.services;

import com.projects.productservice.exceptions.ProductNotFoundException;
import com.projects.productservice.models.Category;
import com.projects.productservice.models.Product;
import com.projects.productservice.repositories.CategoryRepository;
import com.projects.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
         return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description, double price, String image, String categoryName) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        if(optionalCategory.isEmpty()){
            Category category=new Category();
            category.setName(categoryName);
            product.setCategory(categoryRepository.save(category));
        }else{
            product.setCategory(optionalCategory.get());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProductPrice(long productId, double price) throws ProductNotFoundException {
        Product product = getProductById(productId);
        product.setPrice(price);
        return productRepository.save(product);
    }

    @Override
    public Product updateProductImage(long productId, String image) throws ProductNotFoundException {
        Product product = getProductById(productId);
        product.setImage(image);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long productId) throws ProductNotFoundException {
        productRepository.deleteById(productId);
    }
}
