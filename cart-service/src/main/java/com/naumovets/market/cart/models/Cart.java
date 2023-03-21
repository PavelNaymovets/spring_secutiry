package com.naumovets.market.cart.models;

import com.naumovets.market.api.dto.product.ProductDto;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> list;
    @Getter
    private BigDecimal totalPrice;

    public Cart() {
        list = new ArrayList<>();
    }

    public void add(ProductDto product) {
        list.add(new CartItem(product.getId(), product.getTitle(), 1, product.getCost(), product.getCost()));
        recalculate();
    }

    public void delete(ProductDto product) {
        list.removeIf(cartItem -> cartItem.getProductId().equals(product.getId()));
        recalculate();
    }

    public void deleteAll() {
        list.clear();
        recalculate();
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        list.forEach(cartItem -> totalPrice = totalPrice.add(cartItem.getPrice()));
    }

    public boolean changeQuantity(Long id, Integer delta) {
        CartItem item = list.stream().filter(cartItem -> cartItem.getProductId().equals(id)).findFirst().orElse(null);
        if (item == null || (item.getQuantity() == 0 && delta == -1)) {
            return false;
        } else {
            item.setQuantity(item.getQuantity() + delta);
            item.setPrice(item.getPricePerProduct().multiply(BigDecimal.valueOf(item.getQuantity())));
            recalculate();
            return true;
        }
    }
}


