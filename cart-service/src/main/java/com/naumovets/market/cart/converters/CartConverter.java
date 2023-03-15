package com.naumovets.market.cart.converters;

import com.naumovets.market.api.dto.cart.CartDto;
import com.naumovets.market.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setList(cart.getList().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()));

        return cartDto;
    }
}
