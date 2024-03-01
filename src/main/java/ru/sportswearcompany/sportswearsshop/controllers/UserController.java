package ru.sportswearcompany.sportswearsshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sportswearcompany.sportswearsshop.model.ShoppingCart;
import ru.sportswearcompany.sportswearsshop.service.ShoppingCartService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("user/orderHistory")//вьюшка для корзины с заказами
    public String cartHistory(Principal principal, Model model){
//Principal-оболочка юзера
        List<ShoppingCart> shoppingCarts=shoppingCartService
                .getCompletedShoppingCarts(principal.getName(),true);
        if (shoppingCarts==null){
//если у пользователя нет заказов (вновь добавленный пользователь)
            model.addAttribute("noOrdersYet","User hasn't any orders");
        }
        model.addAttribute("carts",shoppingCarts);
        return "user/orderHistory";
    }

    @GetMapping("user/viewOrder/{id}")//вьюшка для 1 заказа
    public String viewOrder(@PathVariable("id") Long id, Model model){
        ShoppingCart shoppingCart=shoppingCartService.getShoppingCartById(id);
        model.addAttribute("order",shoppingCart);
        return "user/orderHistory";
    }



}
