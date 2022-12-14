package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.entities.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    //    adding an item
    @Transactional
    void addItem(ItemDto itemDto, Long userId, Long collectionId);

    @Transactional
    void addWishItem(ItemDto itemDto, Long userId, Long wishlistId);

    //    delete an item
    @Transactional
    void deleteItemById(Long itemId);

    //    update item
    @Transactional
    void updateItemById(ItemDto itemDto);

    //    find all items by collection
    @Transactional
    List<ItemDto> getAllItemsByCollectionId(Long collectionId);

    List<ItemDto> getAllItemsByWishlistId(Long wishlistId);

    //    get a item by item id
    @Transactional
    Optional<ItemDto> getItemById(Long itemId);

    //    find all items by user
    List<ItemDto> getAllItemsByUserId(Long userId);
}
