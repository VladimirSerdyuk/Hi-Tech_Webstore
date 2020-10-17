package ru.geekbrains.hitech.webstore.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.hitech.webstore.beans.Cart;
import ru.geekbrains.hitech.webstore.entities.Order;
import ru.geekbrains.hitech.webstore.entities.OrderItem;
import ru.geekbrains.hitech.webstore.repositories.OrderRepository;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private Cart cart;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder() {
        Order order = new Order();
        order.setItems(cart.getCartItems());
        System.out.println(order.getItems());
        order.setPrice(cart.getPrice());
        for (OrderItem oi : order.getItems()) {
            oi.setOrder(order);
        }
        cart.clear();
        return orderRepository.save(order);
    }
}