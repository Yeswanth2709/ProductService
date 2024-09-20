package com.projects.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product extends BaseModel{
    private String title;
    private String description;
    private Double price;
    private String image;
    @ManyToOne
    private Category category;
}
