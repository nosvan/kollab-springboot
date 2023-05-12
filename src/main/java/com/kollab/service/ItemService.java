package com.kollab.service;

import com.kollab.dto.ItemDto;
import com.kollab.entity.item.Item;
import com.kollab.repository.ItemRepository;
import com.kollab.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class ItemService {
    private final ItemRepository itemRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(ItemDto itemDto) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item itemToSave = mapDtoToItem(itemDto);
        itemToSave.setCreated_by_id(userServiceImpl.findUserByEmail(customUserDetails.getEmail()).getId());
        itemRepository.save(itemToSave);
    }

    private Item mapDtoToItem(ItemDto itemDto) {
        Item newItem = new Item();
        newItem.setId(itemDto.getId());
        newItem.setName(itemDto.getName());
        newItem.setDescription(itemDto.getDescription());
        newItem.setCategory(itemDto.getCategory());
        newItem.setCategory_id(itemDto.getCategory_id());
        newItem.setItem_type(itemDto.getItem_type());
        newItem.setDate_tz_sensitive(itemDto.getDate_tz_sensitive());
        newItem.setDate_tz_sensitive_end(itemDto.getDate_tz_sensitive_end());
        newItem.setTime_sensitive_flag(itemDto.getTime_sensitive_flag());
        newItem.setDate_range_flag(itemDto.getDate_range_flag());
        newItem.setDate_tz_insensitive(itemDto.getDate_tz_insensitive());
        newItem.setDate_tz_insensitive_end(itemDto.getDate_tz_insensitive_end());
        newItem.setPermission_level(itemDto.getPermission_level());
        newItem.setCreated_by_id(itemDto.getCreated_by_id());
        newItem.setLast_modified_by_id(itemDto.getLast_modified_by_id());
        newItem.setCreated_at(itemDto.getCreated_at());
        newItem.setActive(itemDto.getActive());
        return newItem;
    }

    private ItemDto mapItemToItemDto(Item item) {
        ItemDto newItemDto = new ItemDto();
        newItemDto.setId(item.getId());
        newItemDto.setName(item.getName());
        newItemDto.setDescription(item.getDescription());
        newItemDto.setCategory(item.getCategory());
        newItemDto.setCategory_id(item.getCategory_id());
        newItemDto.setItem_type(item.getItem_type());
        newItemDto.setDate_tz_sensitive(item.getDate_tz_sensitive());
        newItemDto.setDate_tz_sensitive_end(item.getDate_tz_sensitive_end());
        newItemDto.setTime_sensitive_flag(item.getTime_sensitive_flag());
        newItemDto.setDate_range_flag(item.getDate_range_flag());
        newItemDto.setDate_tz_insensitive(item.getDate_tz_insensitive());
        newItemDto.setDate_tz_insensitive_end(item.getDate_tz_insensitive_end());
        newItemDto.setPermission_level(item.getPermission_level());
        newItemDto.setCreated_by_id(item.getCreated_by_id());
        newItemDto.setLast_modified_by_id(item.getLast_modified_by_id());
        newItemDto.setCreated_at(item.getCreated_at());
        newItemDto.setActive(item.getActive());
        return newItemDto;
    }
}
