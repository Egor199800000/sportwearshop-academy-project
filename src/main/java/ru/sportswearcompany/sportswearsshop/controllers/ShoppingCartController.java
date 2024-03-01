package ru.sportswearcompany.sportswearsshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sportswearcompany.sportswearsshop.model.CartItem;
import ru.sportswearcompany.sportswearsshop.model.Item;
import ru.sportswearcompany.sportswearsshop.model.ShoppingCart;
import ru.sportswearcompany.sportswearsshop.model.User;
import ru.sportswearcompany.sportswearsshop.service.ItemService;
import ru.sportswearcompany.sportswearsshop.service.ShoppingCartService;
import ru.sportswearcompany.sportswearsshop.service.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("shoppingCart")
    public String shoppingCart(Principal principal, Model model){
//Principal-обертка юзера
        User user =userService.getUser(principal.getName());
        ShoppingCart searchedCart=shoppingCartService
                .getActiveShoppingCartByUsername(principal.getName(),false);
        if (searchedCart==null){
            ShoppingCart newCart =new ShoppingCart();
            newCart.setUser(user);//bi-directional связь, юзер знает о корзине и корзина о юзере
            shoppingCartService.saveShoppingCarts(newCart);
            user.setShoppingCart(newCart);
            userService.saveUser(user);
            model.addAttribute("cart",newCart);
        }else {
            model.addAttribute("cart",searchedCart);
        }
        return "shoppingCart";
    }

    @GetMapping("item/{id}")
    public String viewItem(@PathVariable(value = "id") Long id,Model model){
        Item item=itemService.getItemById(id);
        List<Item> items=itemService.getAllByItemsByCategory(item.getCategory(),id);
        model.addAttribute("item",item);
        model.addAttribute("items",items);
        System.out.println("total amount without searched item: "+items.size());
        return "item/item";
    }

//ДОБАВЛЕНИЕ ОПРЕДЕЛЕННОГО ТОВАРА В КОРЗИНУ ОПРЕДЕЛЕННОГО ПОЛЬЗОВАТЕЛЯ
    @GetMapping("item/addToCart/{id}")
    public String addToCart(@PathVariable(value = "id") Long id, Principal principal){
        if (principal==null){
            return "login";
        }

        ShoppingCart shoppingCart=shoppingCartService.
                getActiveShoppingCartByUsername(principal.getName(),false);
        Item itemForAdd=itemService.getItemById(id);
//если добавляемый товар уже есть то мы инкрементим его количество
        if (shoppingCart.isItemInCart(itemForAdd)){
//getCartItemByItem()-метод использующий пораждающий паттерн
            CartItem cartItem=shoppingCart.getCartItemByItem(itemForAdd);
            int cartItemQuantity=cartItem.getQuantity()+1;
            cartItem.setQuantity(cartItemQuantity);
            itemService.saveCartItem(cartItem);
        }
        else {
            CartItem newCartItem=new CartItem(itemForAdd,1);
            newCartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(newCartItem);
            itemService.saveCartItem(newCartItem);
            shoppingCartService.saveShoppingCarts(shoppingCart);
        }
        return "redirect:/";
    }


    //ВОЗМОЖНОСТЬ УВЕЛИЧИТЬ  КОЛ ОДНОГО ТОВАРА В КОРЗИНЕ +
    @GetMapping("addQuantity/{id}")
    public String addQuantity(@PathVariable(value = "id") Long id){
        CartItem cartItem=itemService.getCartItemById(id);
        int previousQuantity=cartItem.getQuantity();
        cartItem.setQuantity(previousQuantity+1);
        itemService.saveCartItem(cartItem);
        shoppingCartService.saveShoppingCarts(cartItem.getShoppingCart());

        return "redirect:/shoppingCart";
    }

    //ВОЗМОЖНОСТЬ УМЕНЬШИТЬ  КОЛ ОДНОГО ТОВАРА В КОРЗИНЕ -
    @GetMapping("decreaseQuantity/{id}")
    public String decreaseQuantity(@PathVariable(value = "id") Long id,Principal principal){
        CartItem cartItem=itemService.getCartItemById(id);
        int previousQuantity=cartItem.getQuantity();
        cartItem.setQuantity(previousQuantity-1);

        if (previousQuantity<=1){
            ShoppingCart shoppingCart=shoppingCartService
                    .getActiveShoppingCartByUsername(principal.getName(),false);
            shoppingCart.getCartItems().remove(cartItem);//в случае того если кол товара меньше либо равно 1 при уменьшении мы удаляем корзину
            shoppingCartService.saveShoppingCarts(shoppingCart);
        }

        itemService.saveCartItem(cartItem);
        shoppingCartService.saveShoppingCarts(cartItem.getShoppingCart());

        return "redirect:/shoppingCart";
    }

    //ОБЩИЙ ИТОГ ПО ЗАКАЗУ
    @GetMapping("order/{id}")
    public String checkOut(@PathVariable(value = "id")Long id
            , Principal principal
            , RedirectAttributes attributes){
        User user=userService.getUser(principal.getName());
        ShoppingCart shoppingCart=shoppingCartService.getShoppingCartById(id);
        shoppingCart.setCompleted(true);
        shoppingCart.setPrice(shoppingCart.calcCartPrice());
        shoppingCart.setDate(new Date());
        shoppingCartService.saveShoppingCarts(shoppingCart);

        user.getShoppingCarts().add(shoppingCart);
        ShoppingCart newShoppingCart=new ShoppingCart();
        newShoppingCart.setUser(user);
        user.setShoppingCart(newShoppingCart);
        userService.saveUser(user);
        attributes.addFlashAttribute("checkoutSuccess",shoppingCart.calcCartPrice());
        attributes.addFlashAttribute("checkoutSuccess2",shoppingCart.calcNumberOfItems());
        return "redirect:/";
    }



}
