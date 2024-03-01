package ru.sportswearcompany.sportswearsshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.ShoppingCart;
import ru.sportswearcompany.sportswearsshop.model.User;
import ru.sportswearcompany.sportswearsshop.repo.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String username){
        return userRepository.findUserByUsername(username);
    }

    public User getUser(Long id){
        return userRepository.findUserById(id);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public List<User> getUserWithAdvanceSearch(String search){
//два параметра, либо username либо email
        return userRepository.findAllByUsernameContainingOrEmailContaining(search,search);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

//регистрация юзера с проверкой уникальности имени и логина
    public String registerUser(User user){
        User checkedUsernameByUsername=userRepository.findUserByUsername(user.getUsername());
        User checkedUserByEmail=userRepository.findByEmail(user.getEmail());

        if (checkedUsernameByUsername!=null && checkedUserByEmail !=null){
            return "notUniqueUsernameAndEmail";
        }
        if (checkedUsernameByUsername!=null){
            return "notUniqueUsername";
        }
        if (checkedUserByEmail !=null){
            return "notUniqueEmail";
        }
        user.setEnable(true);
//интерфейс PasswordEncoder из Spring security
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        String encodedPassword=encoder.encode(user.getPassword());//шифруем пароль
        user.setPassword(encodedPassword);
        user.setRoles("USER");
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);

        user.setEnable(true);

        userRepository.save(user);

        return "success";
    }



}
