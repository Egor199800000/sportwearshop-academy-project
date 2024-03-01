package ru.sportswearcompany.sportswearsshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportswearcompany.sportswearsshop.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    List<User> findAll();

    User findUserById(Long id);

    User findUserByUsername(String username);

    User findByUsername(String s);

    User findByEmail(String email);

//ПОИСК ВСЕХ СПИСКОВ ЮЗЕРОВ ПО ИМЕНИ ИЛИ ПО EMAIL
    List<User> findAllByUsernameContainingOrEmailContaining(String username,String Email);



}
