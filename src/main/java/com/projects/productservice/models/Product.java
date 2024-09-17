package com.projects.productservice.models;

import lombok.Data;

@Data
public class Product extends BaseModel{
    private String title;
    private String description;
    private Double price;
    private String image;

    private Category category;
}
