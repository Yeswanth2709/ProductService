package com.projects.productservice.services;

import com.projects.productservice.dtos.FakeStoreProductDto;
import com.projects.productservice.exceptions.ProductNotFoundException;
import com.projects.productservice.models.Product;
import com.projects.productservice.utils.ProductUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
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

    @Override
    public Product createProduct(String title, String description, double price, String image, String category) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto productDto=new FakeStoreProductDto();
        productDto.setTitle(title);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setImage(image);
        productDto.setCategory(category);
        FakeStoreProductDto newProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products",productDto, FakeStoreProductDto.class).getBody();;
        return ProductUtils.convertDtotoProduct(newProductDto);
    }

    @Override
    public Product updateProductPrice(long productId, double price) throws ProductNotFoundException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDto productDto = restTemplate.getForEntity("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class).getBody();
        if(productDto==null){
            throw new ProductNotFoundException("Invalid Product Id");
        }
        productDto.setPrice(price);
        FakeStoreProductDto newProductDto = updateForEntity("https://fakestoreapi.com/products/{id}", productDto, FakeStoreProductDto.class, productId).getBody();
        return ProductUtils.convertDtotoProduct(newProductDto);
    }

    @Override
    public Product updateProductImage(long productId, String image) throws ProductNotFoundException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDto productDto = restTemplate.getForEntity("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class).getBody();
        if(productDto==null){
            throw new ProductNotFoundException("Invalid Product Id");
        }
        productDto.setImage(image);
        FakeStoreProductDto newProductDto = updateForEntity("https://fakestoreapi.com/products/{id}", productDto, FakeStoreProductDto.class, productId).getBody();
        return ProductUtils.convertDtotoProduct(newProductDto);
    }

    @Override
    public Product deleteProduct(long productId) throws ProductNotFoundException {
        FakeStoreProductDto deletedProductDto = deleteForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId).getBody();
        if(deletedProductDto==null){
            throw new ProductNotFoundException("Product Id Not Found");
        }
        return ProductUtils.convertDtotoProduct(deletedProductDto);
    }

    private <T> ResponseEntity<T> updateForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    private  <T> ResponseEntity<T> deleteForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
    }
}
