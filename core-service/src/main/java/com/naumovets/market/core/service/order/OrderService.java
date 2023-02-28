package com.naumovets.market.core.service.order;

import com.naumovets.market.api.dto.cart.CartDto;
import com.naumovets.market.api.dto.cart.CartItemDto;
import com.naumovets.market.core.entities.authentication.User;
import com.naumovets.market.core.entities.order.Order;
import com.naumovets.market.core.entities.order.OrderItem;
import com.naumovets.market.core.repositories.order.OrderRepository;
import com.naumovets.market.core.service.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(User user) {
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(100);

        CartItemDto cartItemD = new CartItemDto();
        cartItemD.setProductId(1L);
        cartItemD.setQuantity(1);
        cartItemD.setPrice(25);
        cartItemD.setPricePerProduct(5);
        cartItemD.setProductTitle("Bread");

        cartDto.setList(List.of(cartItemD));


        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getList().stream().map(
                cartItemDto -> new OrderItem(
                        productService.findById(cartItemDto.getProductId()).get(),
                        order,
                        cartItemDto.getQuantity(),
                        cartItemDto.getPricePerProduct(),
                        cartItemDto.getPrice()
                )
        ).collect(Collectors.toList()));

        orderRepository.save(order);
    }
}
