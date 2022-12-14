package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.entities.User;
import com.devmountain.collectionApp.entities.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devmountain.collectionApp.repositories.CollectionRepository;
import com.devmountain.collectionApp.repositories.WishlistRepository;
import com.devmountain.collectionApp.repositories.ItemRepository;
import com.devmountain.collectionApp.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ItemRepository itemRepository;

    //    adding an item
    @Override
    @Transactional
    public void addItem(ItemDto itemDto, Long userId, Long collectionId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        Item item = new Item(itemDto);
        userOptional.ifPresent(item::setUser);
        collectionOptional.ifPresent(item::setCollection);
        itemRepository.saveAndFlush(item);

    }

    @Override
    @Transactional
    public void addWishItem(ItemDto itemDto, Long userId, Long wishlistId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);
        Item item = new Item(itemDto);
        userOptional.ifPresent(item::setUser);
        wishlistOptional.ifPresent(item::setWishlist);
        itemRepository.saveAndFlush(item);

    }

    //    delete an item
    @Override
    @Transactional
    public void deleteItemById(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        itemOptional.ifPresent(item -> itemRepository.delete(item));
    }

    //    update item
    @Override
    @Transactional
    public void updateItemById(ItemDto itemDto) {
        Optional<Item> itemOptional = itemRepository.findById(itemDto.getId());
        itemOptional.ifPresent(item -> {
            item.setName(itemDto.getName());
            item.setBrand(itemDto.getBrand());
            item.setStock_photo(itemDto.getStock_photo());
            item.setOriginal_price(itemDto.getOriginal_price());
            item.setUser_photo(itemDto.getUser_photo());
            item.setAmount_paid(itemDto.getAmount_paid());
            item.setDate_acquired(itemDto.getDate_acquired());
            item.setCurrent_location(itemDto.getCurrent_location());
            item.setCurrent_value(itemDto.getCurrent_value());
            item.setKeywords(itemDto.getKeywords());
            item.setNotes(itemDto.getNotes());
            itemRepository.saveAndFlush(item);
        });
    }

    //    find all items by collection
    @Override
    public List<ItemDto> getAllItemsByCollectionId(Long collectionId){
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()){
            List<Item> itemList = itemRepository.findAllByCollectionEquals(collectionOptional.get());
            return itemList.stream().map(item -> new ItemDto(item)).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public List<ItemDto> getAllItemsByWishlistId(Long wishlistId){
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);
        if (wishlistOptional.isPresent()){
            List<Item> itemList = itemRepository.findAllByWishlistEquals(wishlistOptional.get());
            return itemList.stream().map(item -> new ItemDto(item)).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    //    get a item by item id
    @Override
    public Optional<ItemDto> getItemById(Long itemId){
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isPresent()){
            return Optional.of(new ItemDto(itemOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<ItemDto> getAllItemsByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            List<Item> itemList = itemRepository.findAllByUserEquals(userOptional.get());
            return itemList.stream().map(item -> new ItemDto(item)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
