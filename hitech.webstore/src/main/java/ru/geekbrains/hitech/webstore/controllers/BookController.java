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
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    // context-path: http://localhost:8189/webstore/books

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String showBooksPage(Model model,
                                   @RequestParam(name = "p", defaultValue = "1") Integer pageIndex,
                                   @RequestParam(name = "min_price", required = false) Integer minPrice,
                                   @RequestParam(name = "max_price", required = false) Integer maxPrice,
                                   @RequestParam(name = "title", required = false) String title,
                                   @RequestParam(name = "author", required = false) String author
    ) {
//        List<Item> books = itemService.findItemsByCategory("Book");
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
        specification = specification.and(ItemSpecifications.categoryContains("Book"));
        Page<Item> page = itemService.findAll(pageIndex - 1, 10, specification);
        model.addAttribute("books", page.getContent())
                .addAttribute("minPrice", minPrice)
                .addAttribute("maxPrice", maxPrice)
                .addAttribute("title", title)
                .addAttribute("author", author);
        return "books";
    }

//    1-й вариант реализации метода showBooksPage
//    @GetMapping("/books")
//    public String showBooksPage(Model model) {
//        List<Item> books = itemService.findItemsByCategory("Book");
//        model.addAttribute("books", books);
//        return "books";
//    }

    // GET http://localhost:8189/webstore/books/add?category=Book&title=Время+Московское&author=Александр+Зорич&price=600
    @GetMapping("/add")
    public String addNewBook(@ModelAttribute Item item) {
        itemService.add(item);
        return "control_panel";
    }

    // GET http://localhost:8189/webstore/books/delete?id=11
    @GetMapping("/delete")
    public String deleteBookById(Model model, Long id) {
        Optional<Item> book = itemService.findById(id);
        if (book.isPresent()) {
            itemService.deleteById(id);
            return "control_panel";
        } else {
            model.addAttribute("exception","ОШИБКА: Книга с таким ID отсуствует в БД.");
            return "exception";
        }
    }
}