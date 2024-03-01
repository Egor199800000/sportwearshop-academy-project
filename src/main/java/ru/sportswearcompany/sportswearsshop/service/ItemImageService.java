package ru.sportswearcompany.sportswearsshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.ItemImage;
import ru.sportswearcompany.sportswearsshop.repo.ItemImageRepository;



@Service
@RequiredArgsConstructor
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    public void saveItemImage(ItemImage itemImage){
        itemImageRepository.save(itemImage);
    }

    public void deleteImageById(Long id){
        itemImageRepository.deleteAllById(id);
    }
}
