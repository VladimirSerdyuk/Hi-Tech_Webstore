package ru.geekbrains.hitech.webstore.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.hitech.webstore.entities.Item;

public class ItemSpecifications {
        public static Specification<Item> priceGreaterThanOrEqual(Integer value) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
        };
    }

    public static Specification<Item> priceLessThanOrEqual(Integer value) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
        };
    }

    public static Specification<Item> titleContains(String word) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Item> authorContains(String word) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("author"), "%" + word + "%");
    }

    public static Specification<Item> categoryContains(String word) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("category"), "%" + word + "%");
    }
}