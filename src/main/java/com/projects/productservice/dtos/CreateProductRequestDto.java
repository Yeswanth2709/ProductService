package com.projects.productservice.dtos;

import lombok.Data;

@Data
public class CreateProductRequestDto {
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
}
