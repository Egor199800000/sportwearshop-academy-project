package ru.sportswearcompany.sportswearsshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.CartItem;
import ru.sportswearcompany.sportswearsshop.model.Item;
import ru.sportswearcompany.sportswearsshop.repo.CartItemRepository;
import ru.sportswearcompany.sportswearsshop.repo.ItemImageRepository;
import ru.sportswearcompany.sportswearsshop.repo.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;

    private final CartItemRepository cartItemRepository;

//Получение всего списка товаров
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

//ПОЛУЧЕНИЕ СПИСКА ДОСТУПНЫХ ДЛЯ ПОКУПКИ ТОВАРОВ
    public List<Item> getAllActiveItems(){
        return itemRepository.findAllByActiveTrue();
    }

//ПОЛУЧАЕМ СПИСОК ДОСТУПНЫХ ДЛЯ ЗАКАЗА ПОЗИЦИЙ ОТСОРТИРОВАННЫХ ПО КАТЕГОРИЯМ
public List<Item> getAllByItemsByCategory(String category,Long itemId){
   List<Item> allItemsByCategory=itemRepository.findAllByActiveTrueAndCategory(category);//общий список товаров доступных для заказа по этой категории
   Item item=itemRepository.findItemById(itemId);//получим отдельный товар относящийся к этой категории
   allItemsByCategory.remove(item);//если товар не удовлетворяет нашему запросу, то удаляем его из общего листа
   return allItemsByCategory;//отображаем оставшийся товар по нужной категории
}

//СОХРАНЕНИЕ ТОВАРА
public void saveItem(Item item){
        itemRepository.save(item);
}

//ПОЛУЧЕНИЕ ТОВАРА ПО ID
public Item getItemById(Long id){
        return  itemRepository.findItemById(id);
}

//СОХРАНЕНИЕ КОРЗИНЫ ПОКУПАТЕЛЯ
public void saveCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
}

public CartItem getCartItemById(Long id){
    return  cartItemRepository.getById(id);
}





}
