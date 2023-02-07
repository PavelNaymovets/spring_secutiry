package com.naumovets.spring_secutiry.cart;

import com.naumovets.spring_secutiry.entities.products.Product;
import com.naumovets.spring_secutiry.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Cart {
    private List<CartItem> list;
    @Getter
    private int totalPrice;

    public Cart() {
        list = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(list);
    }

    public void add(Product product) {
        CartItem item = list.stream().filter(cartItem -> cartItem.getProductId().equals(product.getId())).findFirst().orElse(null);
        if (item == null) {
            list.add(new CartItem(product.getId(), product.getTitle(), 1, product.getCost(), product.getCost()));
        } else {
            item.setQuantity(item.getQuantity() + 1);
            item.setPrice(item.getPricePerProduct() * item.getQuantity());
        }
        recalculate();
    }

    public void delete(Product product) {
        list.removeIf(cartItem -> cartItem.getProductId().equals(product.getId()));
        recalculate();
    }

    private void recalculate() {
        totalPrice = 0;
        list.forEach(cartItem -> totalPrice = totalPrice + cartItem.getPrice());
        log.info(String.valueOf(totalPrice));
    }

    public void changeQuantity(Long id, Integer delta) {
        CartItem item = list.stream().filter(cartItem -> cartItem.getProductId().equals(id)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Cart item not found, id: " + id));
        if (item.getQuantity() == 0 && delta == -1) {
            return;
        } else {
            item.setQuantity(item.getQuantity() + delta);
            item.setPrice(item.getPricePerProduct() * item.getQuantity());
            recalculate();
        }
    }
}


