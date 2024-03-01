package ru.sportswearcompany.sportswearsshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.Item;
import ru.sportswearcompany.sportswearsshop.repo.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ItemRepository itemRepository;

//МЕТОД КОТОРЫЙ ИЩЕТ ТОВАР ПО ОДНОЙ ИЗ КАТЕГОРИЙ: ИМЯ, КАТЕГОРИЯ, ОПИСАНИЕ, БРЕНД
    public List<Item> getSearchItems(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContaining(search,search,search,search);
    }

//МЕТОД КОТОРЫЙ ИЩЕТ ТОВАР ПО ОДНОЙ ИЗ КАТЕГОРИЙ: ИМЯ, КАТЕГОРИЯ, ОПИСАНИЕ, БРЕНД, НО ПО КОНКРЕТНОМУ ИМЕНИ
    public List<Item> getSearchItemsOrderByName(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByName(search,search,search,search);
    }

//МЕТОД КОТОРЫЙ ИЩЕТ ТОВАР ПО ОДНОЙ ИЗ КАТЕГОРИЙ: ИМЯ, КАТЕГОРИЯ, ОПИСАНИЕ, БРЕНД, НО ПО КОНКРЕТНОЙ КАТЕГОРИИ
    public List<Item> getSearchItemsOrderByCategory(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByCategory(search,search,search,search);
    }

    //МЕТОД КОТОРЫЙ ИЩЕТ ТОВАР ПО ОДНОЙ ИЗ КАТЕГОРИЙ: ИМЯ, КАТЕГОРИЯ, ОПИСАНИЕ, БРЕНД, НО ПО КОНКРЕТНОМУ ИМЕНИ
    public List<Item> getSearchItemsOrderByBrand(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByBrand(search,search,search,search);
    }

    //МЕТОД КОТОРЫЙ ИЩЕТ ТОВАР ПО ОДНОЙ ИЗ КАТЕГОРИЙ: ИМЯ, КАТЕГОРИЯ, ОПИСАНИЕ, БРЕНД, НО ПО ВОЗРАСТАНИЮ ЦЕНЫ
    public List<Item> getSearchItemsOrderByPriceAsc(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceAsc(search,search,search,search);
    }

    //МЕТОД КОТОРЫЙ ИЩЕТ ТОВАР ПО ОДНОЙ ИЗ КАТЕГОРИЙ: ИМЯ, КАТЕГОРИЯ, ОПИСАНИЕ, БРЕНД, НО ПО УБЫВАНИЮ ЦЕНЫ
    public List<Item> getSearchItemsOrderByPriceDesc(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceDesc(search,search,search,search);
    }

    public List<Item> getSearchItemsOrderByIdDesc(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByIdDesc(search,search,search,search);
    }



//СОРТИРОВКА ПО id ПО ВОЗРАСТАНИЮ
    public List<Item> sortAllByIdDesc(){
        return itemRepository.findAllByActiveTrueOrderByIdDesc();
    }
//ПОЛУЧЕНИЕ ТОВАРА ПО ИМЕНИ
    public List<Item> getItemsByName(String name){
        return itemRepository.findAllByNameContainingAndActiveTrue(name);
    }


//ПОЛУЧЕНИЕ ТОВАРА ДОСТУПНОГО К ЗАКАЗУ ПО КАТЕГОРИИ
   public List<Item> getItemsByCategory(String category){
    return itemRepository.findAllByCategoryAndActiveTrue(category);
}

    //СОРТИРОВКА ТОВАРА ДОСТУПНОГО К ЗАКАЗУ  ПО ИМЕНИ
    public List<Item> sortAllByName(){
        return itemRepository.findAllByActiveTrueOrderByName();
    }

    //СОРТИРОВКА ТОВАРА ДОСТУПНОГО К ЗАКАЗУ  ПО КАТЕГОРИИ
    public List<Item> sortAllByCategory(){
        return itemRepository.findAllByActiveTrueOrderByCategory();
    }

    //СОРТИРОВКА ТОВАРА ДОСТУПНОГО К ЗАКАЗУ  ПО БРЕНДУ
    public List<Item> sortAllByBrand(){
        return itemRepository.findAllByActiveTrueOrderByBrand();
    }

    //СОРТИРОВКА ТОВАРА ДОСТУПНОГО К ЗАКАЗУ  ПО ЦЕНЕ ПО ВОЗРАСТАНИЮ
    public List<Item> sortAllByPriceAsc(){
        return itemRepository.findAllByActiveTrueOrderByPriceAsc();
    }

    //СОРТИРОВКА ТОВАРА ДОСТУПНОГО К ЗАКАЗУ  ПО ЦЕНЕ ПО УБЫВАНИЮ
    public List<Item> sortAllByPriceDesc(){
        return itemRepository.findAllByActiveTrueOrderByPriceDesc();
    }

    //СОРТИРОВКА ТОВАРА ДОСТУПНОГО К ЗАКАЗУ  ПО Id
    public List<Item> sortAllById(){
        return itemRepository.findAllByActiveTrueOrderById();
    }
}
