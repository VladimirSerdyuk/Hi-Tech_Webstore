package ru.geekbrains.hitech.webstore.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "price")
    private Integer price;

    @Column(name = "price_per_item")
    private Integer pricePerItem;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Item item) {
        this.item = item;
        this.quantity = 1;
        this.pricePerItem = item.getPrice();
        this.price = quantity * pricePerItem;
    }

    public void incrementQuantity() {
        quantity++;
        price = pricePerItem * quantity;
    }

    public void decrementQuantity() {
        quantity--;
        price = pricePerItem * quantity;
    }
}