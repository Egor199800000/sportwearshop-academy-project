package ru.sportswearcompany.sportswearsshop.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
@Entity
public class Item {
//Сущность товара который доступен к заказу
//лежит либо не лежит в корзине
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private float price;

    @Column(length = 1000)// соответствие ограничения варчара в постгрессе
    private String description;

    private String category;

    private String brand;

//доступен ли товар к заказу
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")//один товар-много картинок
    private List<ItemImage> images=new java.util.ArrayList<>();

//МЕТОД КОТОРЫЙ ВОЗВРАЩАЕТ СПИСОК ПУТЕЙ ДО НАШИХ ИЗОБРАЖЕНИЙ ТОВАРА
    public List<String> imagesPaths(){
        List<String> imagePaths=new ArrayList<>();

        for (ItemImage itemImage: images){
            imagePaths.add
                    (new String(Base64.getDecoder().decode(itemImage.getImgPath())));
        }
        return imagePaths;
    }


    public Item(String name, float price, String description, String category, String brand) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
    }

    public Item() {
    }

    public Item(String name, float price, String description, String category, String brand, List<ItemImage> images) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.images = images;
    }
}
