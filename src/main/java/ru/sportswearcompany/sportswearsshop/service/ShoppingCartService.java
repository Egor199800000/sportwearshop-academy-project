package ru.sportswearcompany.sportswearsshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.ShoppingCart;
import ru.sportswearcompany.sportswearsshop.repo.ShoppingCartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart getShoppingCartById(Long id){
        return shoppingCartRepository.findShoppingCartById(id);
    }

//ПОЛУЧЕНИЕ АКТИВНОЙ КОРЗИНЫ
    public ShoppingCart getActiveShoppingCartByUsername(String username, Boolean isCompleted){//параметр isCompleted-если корзина завершена то true, если она активна то не завершена, значит false (в процессе покупки)
//в случае того если у юзера несколько корзин
        List<ShoppingCart> shoppingCarts=shoppingCartRepository.findAllByUserUsernameAndCompleted(username, isCompleted);

        if (!shoppingCarts.isEmpty()){
            return shoppingCarts.get(0);
        }
        return new ShoppingCart();
    }

    public List<ShoppingCart> getCompletedShoppingCarts(String username, Boolean isCompleted){
        return shoppingCartRepository.findAllByUserUsernameAndCompletedOrderByDateDesc(username,isCompleted);
    }

    public List<ShoppingCart> getAllShoppingCarts(){
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart saveShoppingCarts(ShoppingCart shoppingCart){
        return shoppingCartRepository.save(shoppingCart);
    }

    public void deleteShoppingById(Long id){
        shoppingCartRepository.deleteById(id);
    }

}
