package ru.sportswearcompany.sportswearsshop.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;


    @ManyToOne
    //@JoinColumn(name = "user_id")
    private User user;

//сумма заказа в корзине
    private Float price;

//дата заказа-формирования корзины
    private Date date;

//статус по корзине-был ли завершен заказ
    private boolean completed=false;

//содержимое корзины-товарами
    @OneToMany//одна корзина-неск товаров
    private List<CartItem> cartItems=new ArrayList<>();


    public ShoppingCart() {
    }

    public ShoppingCart(User user, Float price, List<CartItem> cartItems) {
        this.user = user;
        this.price = price;
        this.cartItems = cartItems;
    }

    //ПОДСЧЕТ ОБЩЕГО ПРАЙСА В КОРЗИНЕ
    public Float calcCartPrice() {
        float price=0;
        if (cartItems.isEmpty()){
//если корзина пуста-то цена ноль
            return price;
        }
        for (CartItem cartItem: cartItems){
//подсчет общей цены корзины цена=цена товара в корзине*на кол этого товара в корзине
            price +=(cartItem.getItem().getPrice()*cartItem.getQuantity());
        }
        return price;
    }

//ЛЕЖИТ ЛИ ТОВАР В КОРЗИНЕ
    public boolean isItemInCart(Item item){
        for (CartItem cartItem:cartItems){
//Если id товара лежащего в корзине==id товара на складе, значит он нах в корзине
            return cartItem.getItem().getId()==item.getId();
        }
        return false;
    }

//СООТВЕТСВУЕТ ЛИ ТОВАР ЛЕЖАЩИЙ В КОРЗИНЕ УКАЗАННОМУ ТОВАРУ
    public CartItem getCartItemByItem(Item item){
        for (CartItem cartItem:cartItems){
            if(cartItem.getItem()==item){
                return cartItem;
            }
        }
        return null;
    }

//ПОДСЧЕТ ОБЗЕГО КОЛИЧЕСТВА ТОВАРА В КОРЗИНЕ
    public int calcNumberOfItems(){
        int quantity=0;
        if (cartItems.isEmpty()){
            return quantity;
        }
//пробегаемся по корзине и считаем число товаров учитывая их повтор
        for (CartItem cartItem: cartItems){
            quantity+=cartItem.getQuantity();
        }
        return quantity;
    }

}
