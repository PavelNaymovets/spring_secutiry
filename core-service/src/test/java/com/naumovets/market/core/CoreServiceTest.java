package com.naumovets.market.core;

import com.naumovets.market.api.dto.product.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CoreServiceTest {
    @Autowired
    private TestRestTemplate request;

    @Test
    public void productControllerTest() {
        ResponseEntity<ProductDto> response = request.getForEntity("/api/v1/products", ProductDto.class);
        Assertions.assertThat(response).isNotNull();
    }
}
