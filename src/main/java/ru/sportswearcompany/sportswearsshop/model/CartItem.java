package ru.sportswearcompany.sportswearsshop.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class CartItem {
//это сущность товара который лежит в корзине
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;

    @OneToOne//один товар лежащий в корзине соответствует одному товару физически, типо наличия на складе
    private Item item;

//кол товаров
    private int quantity;

    @OneToOne(cascade = CascadeType.ALL)//одно наименование относится к одной корзине
    private ShoppingCart shoppingCart;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
