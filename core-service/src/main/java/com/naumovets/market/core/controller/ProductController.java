package com.naumovets.market.core.controller;

import com.naumovets.market.core.converters.ProductConverter;
import com.naumovets.market.core.entities.products.Product;
import com.naumovets.market.core.service.products.ProductService;
import com.naumovets.market.core.dto.ProductDto;
import com.naumovets.market.core.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> getAll(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        System.out.println(page + " " + minPrice + " " + maxPrice + " " + namePart);
        if(page < 1) {
            page = 1;
        }
        return productService.findAll(minPrice, maxPrice, namePart, page).map(ProductConverter::entityToDto);
    }

    @PostMapping
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productConverter.dtoToEntity(productDto);
        newProduct = productService.addNewProduct(newProduct);
        return ProductConverter.entityToDto(newProduct);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.findById(id).map(ProductConverter::entityToDto).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    public void changeCost(@RequestParam Long id, @RequestParam Integer delta) {
        productService.changeCost(id, delta);
    }

}
