package com.naumovets.market.core.service.products;

import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.core.converters.ProductConverter;
import com.naumovets.market.core.entities.products.Category;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.repositories.products.CategoryRepository;
import com.naumovets.market.core.repositories.products.specifications.ProductSpecification;
import com.naumovets.market.core.repositories.products.ProductRepository;
import com.naumovets.market.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String namePart, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.priceLessOrEqualsThan(maxPrice));
        }
        if (namePart != null) {
            spec = spec.and(ProductSpecification.titleLike(namePart));
        }

        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    @Transactional
    public void changeCost(Long id, Integer delta) {
        Product product = productRepository.findById(id).get();
        product.setCost(product.getCost().add(BigDecimal.valueOf(delta)));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCostBetween(Integer min, Integer max) {
        return productRepository.findAllByCostBetween(min, max);
    }

    public List<Product> findByCostLessThan(Integer value) {
        return productRepository.findByCostLessThan(value);
    }

    public List<Product> findByCostGreaterThan(Integer value) {
        return productRepository.findByCostGreaterThan(value);
    }

    public Product addNewProduct(ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        return productRepository.save(product);
    }

    @Transactional
    public void changeProduct(ProductDto productDto) {
        String[] oldNewTitle = productDto.getTitle().split(",");
        String oldTitle = oldNewTitle[0];
        String newTitle = oldNewTitle[1];

        Category newCategory = categoryRepository.findByTitle(productDto.getCategoryTitle()).get();

        Product product = productRepository.findByTitle(oldTitle).get();
        product.setTitle(newTitle);
        product.setCategory(newCategory);
        product.setCost(productDto.getCost());
    }
}
