package com.projects.productservice.services;

import com.projects.productservice.clients.fakeStore.FakeStoreApiClient;
import com.projects.productservice.dtos.FakeStoreProductDto;
import com.projects.productservice.exceptions.ProductNotFoundException;
import com.projects.productservice.models.Product;
import com.projects.productservice.utils.ProductUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FakeStoreProductServiceImpl implements ProductService {
    private final FakeStoreApiClient fakeStoreApiClient;

    public FakeStoreProductServiceImpl(FakeStoreApiClient fakeStoreApiClient) {
        this.fakeStoreApiClient = fakeStoreApiClient;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        FakeStoreProductDto productDto = fakeStoreApiClient.getProductById(productId);
        if(productDto==null){
            throw new ProductNotFoundException("Invalid Product Id");
        }
        return ProductUtils.convertDtotoProduct(productDto);

    }

    @Override
    public Page<Product> getAllProducts(int pageSize,int pageNumber) {
        FakeStoreProductDto[] productDtos = fakeStoreApiClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto productDto:productDtos){
            products.add(ProductUtils.convertDtotoProduct(productDto));
        }
        return null;
    }

    @Override
    public Product createProduct(String title, String description, double price, String image, String categoryName) {
        FakeStoreProductDto productDto=new FakeStoreProductDto();
        productDto.setTitle(title);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setImage(image);
        productDto.setCategory(categoryName);
        FakeStoreProductDto newProductDto = fakeStoreApiClient.createProduct(productDto);
        return ProductUtils.convertDtotoProduct(newProductDto);
    }

    @Override
    public Product updateProductPrice(long productId, double price) throws ProductNotFoundException {
        FakeStoreProductDto productDto = fakeStoreApiClient.getProductById(productId);
        if(productDto==null){
            throw new ProductNotFoundException("Invalid Product Id");
        }
        productDto.setPrice(price);
        FakeStoreProductDto newProductDto = fakeStoreApiClient.updateProduct(productId, productDto);
        return ProductUtils.convertDtotoProduct(newProductDto);
    }

    @Override
    public Product updateProductImage(long productId, String image) throws ProductNotFoundException {
        FakeStoreProductDto productDto = fakeStoreApiClient.getProductById(productId);
        if(productDto==null){
            throw new ProductNotFoundException("Invalid Product Id");
        }
        productDto.setImage(image);
        FakeStoreProductDto newProductDto = fakeStoreApiClient.updateProduct(productId,productDto);
        return ProductUtils.convertDtotoProduct(newProductDto);
    }

    @Override
    public void deleteProduct(long productId) throws ProductNotFoundException {
        FakeStoreProductDto deletedProductDto = fakeStoreApiClient.deleteProduct(productId);
        if(deletedProductDto==null){
            throw new ProductNotFoundException("Product Id Not Found");
        }
    }

}
