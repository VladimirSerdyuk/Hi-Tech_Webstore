package ru.geekbrains.hitech.webstore.beans;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.hitech.webstore.entities.Item;
import ru.geekbrains.hitech.webstore.entities.OrderItem;
import ru.geekbrains.hitech.webstore.exceptions.ResourceNotFoundException;
import ru.geekbrains.hitech.webstore.services.ItemService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {
    private ItemService itemService;
    private List<OrderItem> cartItems;
    private Integer price;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostConstruct
    public void initialize(){
        cartItems = new ArrayList<>();
    }

    public OrderItem findCartItemById(Long id){
        int itemIndex = -1;
        for (OrderItem oi : cartItems){
            if (id.equals(oi.getId())){
                itemIndex++;
            }
        }
        if (itemIndex > -1){
            return cartItems.get(itemIndex);
        }
        return null;
    }

    public void incrementOrAddItemToCart(Long itemId) {
        System.out.println("Метод: incrementOrAddItemToCart, id: "+itemId);
        for (OrderItem oi : cartItems) {
            if(oi.getItem().getId().equals(itemId)) {
                oi.incrementQuantity();
                return;
            }
        }
        Item item = itemService.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить товар в корзину. Товара с ID "+itemId+" не существает."));
        cartItems.add(new OrderItem(item));
        recalculate();
    }

    public void removeItemFromCart(Long itemId) {
        cartItems.removeIf(oi -> oi.getItem().getId().equals(itemId));
        recalculate();
    }

    public void decrementItemInCart(Long itemId) {
        Iterator<OrderItem> iterator = cartItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            if(oi.getItem().getId().equals(itemId)) {
                oi.decrementQuantity();
                if (oi.getQuantity() == 0){
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void clear() {
        cartItems.clear();
        recalculate();
    }

    public void recalculate() {
        price = 0;
        for (OrderItem oi : cartItems) {
            price += oi.getPrice();
        }
    }

    public void deleteItemFromCart(OrderItem orderItem){
        int itemIndex = cartItems.indexOf(orderItem);
        if (itemIndex > -1){
            cartItems.remove(itemIndex);
        }
    }
}