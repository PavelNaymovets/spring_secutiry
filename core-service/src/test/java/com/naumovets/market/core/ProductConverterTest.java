package com.naumovets.market.core;

import com.naumovets.market.api.dto.product.ProductDto;
import com.naumovets.market.core.converters.ProductConverter;
import com.naumovets.market.core.entities.products.Category;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.service.products.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = ProductConverter.class)
class ProductConverterTest {
	@Autowired
	private ProductConverter productConverter;

	@MockBean
	private CategoryService categoryService;
	private Product product;
	private ProductDto productDto;
	private Category mockCategory;
	private List<Product> productList;

	@BeforeEach
	public void initMockBeanBehavior() {
		product = new Product(1L, "Молоко", 100, new Category(1L, "Другое", new ArrayList<>()));
		productList = new ArrayList<>(List.of(product));
		productDto = new ProductDto(1L, "Молоко", 100, "Другое");
		mockCategory = new Category(1L, "Другое", productList);

		Mockito.doReturn(Optional.of(mockCategory))
				.when(categoryService).findByTitle(productDto.getCategoryTitle());
	}

	@Test
	void entityToDtoTest() {
		ProductDto testProductDto = ProductConverter.entityToDto(product);

		Assertions.assertNotNull(testProductDto);
		Assertions.assertEquals(1L, testProductDto.getId());
		Assertions.assertEquals(100, testProductDto.getCost());
		Assertions.assertEquals("Молоко", testProductDto.getTitle());
		Assertions.assertEquals("Другое", testProductDto.getCategoryTitle());
	}

	@Test
	void DtoToEntityTest() {
		Product testProduct = productConverter.dtoToEntity(productDto);

		Assertions.assertNotNull(testProduct);
		Assertions.assertEquals(1L, testProduct.getId());
		Assertions.assertEquals(100, testProduct.getCost());
		Assertions.assertEquals("Молоко", testProduct.getTitle());
		Assertions.assertEquals("Другое", testProduct.getCategory().getTitle());
	}

}
