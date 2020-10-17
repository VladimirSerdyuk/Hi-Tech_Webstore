package ru.geekbrains.hitech.webstore.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.hitech.webstore.entities.Item;
import ru.geekbrains.hitech.webstore.repositories.specifications.ItemSpecifications;
import ru.geekbrains.hitech.webstore.services.ItemService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    // context-path: http://localhost:8189/webstore/products

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String showProductsPage(Model model,
                                   @RequestParam(name = "p", defaultValue = "1") Integer pageIndex,
                                   @RequestParam(name = "min_price", required = false) Integer minPrice,
                                   @RequestParam(name = "max_price", required = false) Integer maxPrice,
                                   @RequestParam(name = "title", required = false) String title,
                                   @RequestParam(name = "author", required = false) String author
    ) {
//        List<Item> products = itemService.findItemsByCategory("Product");
        Specification<Item> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(ItemSpecifications.priceGreaterThanOrEqual(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ItemSpecifications.priceLessThanOrEqual(maxPrice));
        }
        if (title != null) {
            specification = specification.and(ItemSpecifications.titleContains(title));
        }
        if (author != null) {
            specification = specification.and(ItemSpecifications.authorContains(author));
        }
        specification = specification.and(ItemSpecifications.categoryContains("Product"));
        Page<Item> page = itemService.findAll(pageIndex - 1, 10, specification);
        model.addAttribute("products", page.getContent())
                .addAttribute("minPrice", minPrice)
                .addAttribute("maxPrice", maxPrice)
                .addAttribute("title", title)
                .addAttribute("author", author);
        return "products";
    }

//    1-й вариант реализации метода showProductsPage
//    @GetMapping("/products")
//    public String showProductsPage(Model model) {
//        List<Item> products = itemService.findItemsByCategory("Product");
//        model.addAttribute("products", products);
//        return "products";
//    }

    // GET http://localhost:8189/webstore/products/add?category=Product&title=cheese&author=OJSC_Moscow_Milk_Combinat&price=217
    @GetMapping("/add")
    public String addNewProduct(@ModelAttribute Item item) {
        itemService.add(item);
        return "control_panel";
    }

    // GET http://localhost:8189/webstore/products/delete?id=11
    @GetMapping("/delete")
    public String deleteProductById(Model model, Long id) {
        Optional<Item> product = itemService.findById(id);
        if (product.isPresent()) {
            itemService.deleteById(id);
            return "control_panel";
        } else {
            model.addAttribute("exception","ОШИБКА: Продукт с таким ID отсуствует в БД.");
            return "exception";
        }
    }
}
