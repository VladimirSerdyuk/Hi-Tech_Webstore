package ru.geekbrains.hitech.webstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.hitech.webstore.entities.Item;
import ru.geekbrains.hitech.webstore.repositories.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> findById(Long id){
        return itemRepository.findById(id);
    }

//    1-й вариант реализации метода findItemsByCategory
//    public List<Item> findItemsByCategory(String category){
//        List<Item> filteredItems = new ArrayList<>();
//        List <Item> items = itemRepository.findAll();
//        for (Item item : items){
//            if (category.equals(item.getCategory())){
//                filteredItems.add(item);
//            }
//        }
//        return filteredItems;
//    }

    public List<Item> findItemsByCategory(String category){
        return itemRepository.findAllByCategory(category);
    }

    public Page<Item> findAll(int page, int pageSize, Specification<Item> specification) {
        return itemRepository.findAll(specification, PageRequest.of(page, pageSize, Sort.Direction.ASC, "id"));
    }

    public Item add(Item item){
        return itemRepository.save(item);
    }

    public void deleteById(Long id){
        itemRepository.deleteById(id);
    }

    public void delete(Item item){
        itemRepository.delete(item);
    }
}
