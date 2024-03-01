package ru.sportswearcompany.sportswearsshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportswearcompany.sportswearsshop.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Item findItemById(Long id);

//товары доступные для заказа
    List<Item> findAllByActiveTrue();

    List<Item> findAllByNameContainingAndActiveTrue(String name);

//ПОЛУЧАЕМ СПИСОК ДОСТУПНЫХ ДЛЯ ЗАКАЗА ПОЗИЦИЙ ОТСОРТИРОВАННЫХ ПО КАТЕГОРИЯМ
    List<Item> findAllByActiveTrueAndCategory(String category);

    List<Item> findAllByCategoryAndActiveTrue(String category);

//товары доступные к заказу-сортировка по id по возрастанию
    List<Item> findAllByActiveTrueOrderById();

//товары доступные к заказу-сортировка по id по убыванию
    List<Item> findAllByActiveTrueOrderByIdDesc();

//товары доступные к заказу-сортировка по имени
    List<Item> findAllByActiveTrueOrderByName();

//товары доступные к заказу-сортировка по категории
    List<Item> findAllByActiveTrueOrderByCategory();

//товары доступные к заказу-сортировка по бренду
    List<Item> findAllByActiveTrueOrderByBrand();

    //товары доступные к заказу-сортировка по цене по возрастанию
    List<Item> findAllByActiveTrueOrderByPriceAsc();

//товары доступные к заказу-сортировка по цене по убыванию
    List<Item> findAllByActiveTrueOrderByPriceDesc();

//метод по поиску:
// по ключ словам (например Java, книги для программирования), доступные к заказу
//либо по категории и доступный к заказу
//либо по бренду и доступный к заказу
//либо по описанию и доступный к заказу
    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContaining
    (String name,String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByName
            (String name,String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByCategory
            (String name,String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByBrand
            (String name,String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceAsc
            (String name,String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceDesc
            (String name,String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByIdDesc
            (String name,String category, String description, String brand);


}
