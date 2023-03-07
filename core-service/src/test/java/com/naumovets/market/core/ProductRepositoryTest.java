package com.naumovets.market.core;

import com.naumovets.market.core.entities.products.Category;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.repositories.products.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void initDbTest() {
        List<Product> products = productRepository.findAll();
        Assertions.assertEquals(2, products.size());
    }
}
