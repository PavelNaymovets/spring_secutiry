package com.naumovets.spring_secutiry.service.products;

import com.naumovets.spring_secutiry.entities.products.Category;
import com.naumovets.spring_secutiry.exceptions.ResourceNotFoundException;
import com.naumovets.spring_secutiry.repositories.products.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

}
