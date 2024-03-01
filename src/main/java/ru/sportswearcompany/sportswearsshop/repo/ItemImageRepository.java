package ru.sportswearcompany.sportswearsshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportswearcompany.sportswearsshop.model.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage,Long> {
//удаление невалидной картинки
    void deleteAllById(Long id);

}
