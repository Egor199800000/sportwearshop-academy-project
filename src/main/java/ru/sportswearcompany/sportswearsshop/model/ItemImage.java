package ru.sportswearcompany.sportswearsshop.model;


import java.util.Base64;

@Data
@Entity
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne//много картинок может быть у одного товара
    private Item item;

//путь к изображениям
    private byte[] imgPath;


    public ItemImage() {
    }

    public ItemImage(Item item, byte[] imgPath) {
        this.item = item;
        this.imgPath = imgPath;
    }

    public String getDecodedImgPath(){
        return new String(Base64.getDecoder().decode(imgPath));
    }
}
