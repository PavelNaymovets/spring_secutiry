package com.naumovets.spring_secutiry.repositories.products;

import com.naumovets.spring_secutiry.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByCostBetween(Integer min, Integer max);

    List<Product> findByCostLessThan(Integer value);

    List<Product> findByCostGreaterThan(Integer value);
}
