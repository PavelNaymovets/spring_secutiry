package com.naumovets.spring_secutiry.service.products;

import com.naumovets.spring_secutiry.entities.products.Product;
import com.naumovets.spring_secutiry.repositories.products.ProductRepository;
import com.naumovets.spring_secutiry.repositories.products.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
        product.setCost(product.getCost() + delta);
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

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }
}
