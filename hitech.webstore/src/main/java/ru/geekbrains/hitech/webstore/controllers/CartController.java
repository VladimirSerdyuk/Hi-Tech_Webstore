package ru.geekbrains.hitech.webstore.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.hitech.webstore.beans.Cart;
import ru.geekbrains.hitech.webstore.entities.Item;
import ru.geekbrains.hitech.webstore.entities.OrderItem;
import ru.geekbrains.hitech.webstore.exceptions.ResourceNotFoundException;
import ru.geekbrains.hitech.webstore.services.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private Cart cart;
    private ItemService itemService;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    // GET http://localhost:8189/webstore/cart
    @GetMapping
    public String showCartPage(Model model) {
        List<OrderItem> cartItems = cart.getCartItems();
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    // GET http://localhost:8189/webstore/cart/add?id=3
    @GetMapping("/add")
    public void addItemToCart(Model model, Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()){
            cart.incrementOrAddItemToCart(item.get().getId());
            response.sendRedirect(request.getHeader("referer"));
        } else {
            model.addAttribute("exception","ОШИБКА: При попытке добавления товара произошла ошибка: товар с таким ID отсуствует в БД.");
            response.sendRedirect("exception");
        }
    }

    // GET http://localhost:8189/webstore/cart/delete?id=3
    @GetMapping("/delete")
    public void deleteItemFromCart(Model model, Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderItem orderItem = cart.findCartItemById(id);
        if (orderItem != null){
            cart.deleteItemFromCart(orderItem);
            response.sendRedirect(request.getHeader("referer"));
        } else {
            model.addAttribute("exception","ОШИБКА: При попытке удаления товара произошла ошибка: товар с ID: "+id+" отсуствует в БД.");
            response.sendRedirect("exception");
        }
    }

    @GetMapping("/quantity/increment")
    public void incrementQuantity(Model model, Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item item = itemService.findById(id).orElseThrow(()-> new ResourceNotFoundException("ОШИБКА: При попытке добавления товара произошла ошибка: товар с ID: "+id+" отсуствует в БД."));
        cart.incrementOrAddItemToCart(id);
        response.sendRedirect(request.getHeader("referer"));
    }
}