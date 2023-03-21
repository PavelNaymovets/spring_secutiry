package com.naumovets.market.core.service.products;

import com.naumovets.market.core.entities.products.Category;
import com.naumovets.market.core.repositories.products.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
