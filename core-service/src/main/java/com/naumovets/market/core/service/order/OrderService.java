package com.naumovets.market.core.service.order;

import com.naumovets.market.api.dto.cart.CartDto;
import com.naumovets.market.api.dto.cart.CartItemDto;
import com.naumovets.market.core.entities.order.Order;
import com.naumovets.market.core.entities.order.OrderItem;
import com.naumovets.market.core.integrations.CartServiceIntegration;
import com.naumovets.market.core.repositories.order.OrderRepository;
import com.naumovets.market.core.service.products.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public void createOrder(String username, String phone, String address) {
        CartDto cartDto = cartServiceIntegration.getCart(username);
        Order order = new Order();
        order.setUsername(username);
        order.setPhone(phone);
        order.setAddress(address);
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
//        cartServiceIntegration.clearCart();
    }

    public List<Order> findByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
}
