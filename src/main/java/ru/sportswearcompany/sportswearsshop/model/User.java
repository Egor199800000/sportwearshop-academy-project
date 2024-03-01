package ru.sportswearcompany.sportswearsshop.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;


    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private boolean isEnable;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    //Если у одного пользователя неск корзин
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
    mappedBy = "user")
    private List<ShoppingCart> shoppingCarts=new ArrayList<>();




    public User() {
    }


    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public User(String username, String password, String roles, String email, List<ShoppingCart> shoppingCarts) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.shoppingCarts = shoppingCarts;
    }

    public User(String username, String password, String roles, String email) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

//ПОЛУЧЕНИЕ СПИСКА РОЛЕЙ
    public List<String> getRoleList(){
        if(this.roles.length()>0){
            System.out.println("User has roles");
            return Arrays.asList(this.roles.split(","));
//Если есть какие то роли то преобразуем в список ролей
        }
        return new ArrayList<>();
    }

    //Общая сумма заказов
    public Float getSumTotalOrder(){
        Float totalOrders=0f;
//пробежка по корзине
        for (ShoppingCart shoppingCart: shoppingCarts){
            totalOrders += shoppingCart.calcCartPrice();
        }
        return totalOrders;
    }




}
