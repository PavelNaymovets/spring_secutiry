package com.naumovets.spring_secutiry.controller;

import com.naumovets.spring_secutiry.cart.Cart;
import com.naumovets.spring_secutiry.converters.ProductConverter;
import com.naumovets.spring_secutiry.dto.ProductDto;
import com.naumovets.spring_secutiry.entities.products.Product;
import com.naumovets.spring_secutiry.exceptions.ResourceNotFoundException;
import com.naumovets.spring_secutiry.service.authentication.UserService;
import com.naumovets.spring_secutiry.service.products.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final UserService userService;
    ProductService productService;
    Cart cart;

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

    @GetMapping("/cart")
    public List<ProductDto> getAllProductCart() {
        return cart.getProducts().stream().map(ProductConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        Product newProduct = ProductConverter.dtoToEntity(productDto);
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

    @DeleteMapping("/cart/{id}")
    public void deleteByIdCart(@PathVariable Long id){
        Product product = productService.findById(id).get();
        cart.deleteById(product);
    }

    @PutMapping
    public void changeCost(@RequestParam Long id, @RequestParam Integer delta) {
        productService.changeCost(id, delta);
    }

    @PostMapping("/cart/{id}")
    public void addProductInCart(@PathVariable Long id) {
        Product product = productService.findById(id).get();
        cart.addProduct(product);
    }
}
