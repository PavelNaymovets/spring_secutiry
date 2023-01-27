package com.naumovets.spring_secutiry.repositories.products.specifications;

import com.naumovets.spring_secutiry.entities.products.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), price);
    }

    public static Specification<Product> priceLessOrEqualsThan(Integer price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }
}
