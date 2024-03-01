package ru.sportswearcompany.sportswearsshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportswearcompany.sportswearsshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


}
