package com.naumovets.spring_secutiry.dto;

import com.naumovets.spring_secutiry.entities.products.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private int cost;
    private String categoryTitle;
}
