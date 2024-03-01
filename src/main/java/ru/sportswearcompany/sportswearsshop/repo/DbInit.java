package ru.sportswearcompany.sportswearsshop.repo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.Item;
import ru.sportswearcompany.sportswearsshop.model.User;

@Service
public class DbInit implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin=new User("admin",
                passwordEncoder.encode("admin"),
                "ADMIN, USER",
                "admin@topjava.com");

        User user=new User("testuser",
                passwordEncoder.encode("testuser"),
                "USER",
                "user@topjava.com");

        Item item=new Item("pants Adidas",5000.0f,"cat: sportwear, subc: pants","pants","Adidas");
        Item item2=new Item("pants Reebok",6000.0f,"cat: sportwear, subc: pants","pants","Reebok");
        Item item3=new Item("pants Puma",4000.0f,"cat: sportwear, subc: pants","pants","Puma");
        Item item4=new Item("pants Nike",5000.0f,"cat: sportwear, subc: pants","pants","Nike");
        Item item5=new Item("pants Fila",2000.0f,"cat: sportwear, subc: pants","pants","Fila");

        Item item6=new Item("shoes Adidas",5000.0f,"cat: shoes, subc: sneakers","sneakers","Adidas");
        Item item7=new Item("shoes Reebok",6000.0f,"cat: shoes, subc: sneakers","sneakers","Reebok");
        Item item8=new Item("shoes Puma",4000.0f,"cat: shoes, subc: sneakers","sneakers","Puma");
        Item item9=new Item("shoes Nike",5000.0f,"cat: shoes, subc: sneakers","sneakers","Nike");
        Item item10=new Item("shoes Fila",2000.0f,"cat: shoes, subc: sneakers","sneakers","Fila");

        userRepository.save(admin);
        userRepository.save(user);
        itemRepository.save(item);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        itemRepository.save(item5);
        itemRepository.save(item6);
        itemRepository.save(item7);
        itemRepository.save(item8);
        itemRepository.save(item9);
        itemRepository.save(item10);

    }

    public DbInit(PasswordEncoder passwordEncoder,
                  UserRepository userRepository,
                  ItemRepository itemRepository,
                  ShoppingCartRepository shoppingCartRepository,
                  CartItemRepository cartItemRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }
}
