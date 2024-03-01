package ru.sportswearcompany.sportswearsshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportswearcompany.sportswearsshop.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    //поиск по логину-по email
    List<ShoppingCart> findAllByUserUsername(String username);

//поиск по логину, завершенная стадия заказа
    List<ShoppingCart> findAllByUserUsernameAndCompleted(String username,Boolean isCompleted);

//поиск по логину, завершенная стадия заказа, дата на убывание
    List<ShoppingCart> findAllByUserUsernameAndCompletedOrderByDateDesc(String username,Boolean isCompleted);

    List<ShoppingCart> findAll();


//поиск заказа по id, неважно завершенная стадия или нет-потенциальный заказ
    ShoppingCart findShoppingCartById(Long id);


    void deleteAllById(Long id);
}
