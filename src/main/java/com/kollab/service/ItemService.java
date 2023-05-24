package com.kollab.service;

import com.kollab.dto.item.ItemDto;
import com.kollab.dto.item.ItemPermissionDto;
import com.kollab.dto.item.ItemUpdateDto;
import com.kollab.entity.User;
import com.kollab.entity.item.Item;
import com.kollab.entity.item.ItemPermission;
import com.kollab.entity.item.VisibilityLevel;
import com.kollab.entity.list.Category;
import com.kollab.repository.ItemPermissionRepository;
import com.kollab.repository.ItemRepository;
import com.kollab.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemPermissionRepository itemPermissionRepository;

    public ItemService(ItemRepository itemRepository, ItemPermissionRepository itemPermissionRepository) {
        this.itemRepository = itemRepository;
        this.itemPermissionRepository = itemPermissionRepository;
    }

    public Item getItem(String itemId) throws Exception {
        Optional<Item> item = itemRepository.findById(Long.valueOf(itemId));
        if(item.isPresent()){
            return item.get();
        } else {
            throw new Exception("Error getting items");
        }
    }

    public List<ItemPermissionDto> getItemPermissions(String itemId) throws Exception {
        Optional<Item> item = itemRepository.findById(Long.valueOf(itemId));
        if(item.isPresent()){
            return ItemPermissionService.mapItemPermissionToItemPermissionDto(item.get().getItemPermissions());
        } else {
            throw new Exception("Error getting items");
        }
    }

    public List<Item> getItems() throws Exception {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return itemRepository.findByCreatedByIdAndCategoryIdIsNull(customUserDetails.getId());
        } catch (Exception exception){
            throw new Exception("Error getting items");
        }
    }

    public List<Item> getItems(Category category, String categoryId){
        return itemRepository.findByCategoryAndCategoryId(category, Long.valueOf(categoryId));
    }

    public ItemDto createItem(ItemDto itemDto) throws Exception {
        Item itemToCreate = mapDtoToItem(itemDto);
        if(itemDto.getCreatedById() == null){
            Long id = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            itemToCreate.setCreatedById(id);
        }
        validateItem(itemToCreate);
        Item item =  itemRepository.save(itemToCreate);
        if(item.getPermissionLevel().equals(VisibilityLevel.PRIVATE)){
            ItemPermission itemPermission = new ItemPermission();
            itemPermission.setUserId(itemToCreate.getCreatedById());
            itemPermission.setItem(item);
            itemPermissionRepository.save(itemPermission);
        }
        Optional<Item> itemToReturn = itemRepository.findById(item.getId());
        if(itemToReturn.isPresent()){
            return mapItemToItemDto(itemToReturn.get());
        } else {
            throw new Exception("Error creating item");
        }
    }

    public void updateItem(ItemUpdateDto itemUpdateDto) throws Exception{
        if(itemUpdateDto.getCreatedById() == null){
            Long id = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            itemUpdateDto.setCreatedById(id);
        }
        Optional<Item> itemToUpdate = itemRepository.findById(itemUpdateDto.getId());
        if(itemToUpdate.isPresent()){
            Item item = itemToUpdate.get();
            mapUpdateDtoToItem(item, itemUpdateDto);
            item.setLastModifiedById(itemUpdateDto.getId());
            itemRepository.save(item);
        } else {
            throw new Exception("Error updating item");
        }
    }

    public void deleteItem(String itemId) throws Exception {
        Optional<Item> itemToDelete = itemRepository.findById(Long.valueOf(itemId));
        if(itemToDelete.isPresent()){
            itemRepository.deleteById(itemToDelete.get().getId());
        } else {
            throw new Exception("Error deleting item");
        }
    }

    private void validateItem(Item item){
        if(item.getTimeSensitiveFlag()){
            item.setDateTzInsensitive(null);
            item.setDateTzInsensitiveEnd(null);
        } else {
            item.setDateTzSensitive(null);
            item.setDateTzSensitiveEnd(null);
        }
        if(!item.getDateRangeFlag()){
            item.setDateTzInsensitiveEnd(null);
            item.setDateTzSensitiveEnd(null);
        }
    }

    private Item mapDtoToItem(ItemDto itemDto) {
        Item newItem = new Item();
        if(itemDto.getId() != null) newItem.setId(itemDto.getId());
        if(itemDto.getName() != null) newItem.setName(itemDto.getName());
        if(itemDto.getDescription() != null) newItem.setName(itemDto.getDescription());
        if(itemDto.getCategory() != null) newItem.setCategory(itemDto.getCategory());
        if(itemDto.getCategoryId() != null) newItem.setCategoryId(itemDto.getCategoryId());
        if(itemDto.getItemType() != null) newItem.setItemType(itemDto.getItemType());
        if(itemDto.getDateTzSensitive() != null) newItem.setDateTzSensitive(itemDto.getDateTzSensitive());
        if(itemDto.getDateTzSensitiveEnd() != null) newItem.setDateTzSensitiveEnd(itemDto.getDateTzSensitiveEnd());
        if(itemDto.getTimeSensitiveFlag() != null) newItem.setTimeSensitiveFlag(itemDto.getTimeSensitiveFlag());
        if(itemDto.getDateRangeFlag() != null) newItem.setDateRangeFlag(itemDto.getDateRangeFlag());
        if(itemDto.getDateTzInsensitive() != null) newItem.setDateTzInsensitive(itemDto.getDateTzInsensitive());
        if(itemDto.getDateTzInsensitiveEnd() != null) newItem.setDateTzInsensitiveEnd(itemDto.getDateTzInsensitiveEnd());
        if(itemDto.getPermissionLevel() != null) newItem.setPermissionLevel(itemDto.getPermissionLevel());
        if(itemDto.getCreatedById() != null) newItem.setCreatedById(itemDto.getCreatedById());
        if(itemDto.getLastModifiedById() != null) newItem.setLastModifiedById(itemDto.getLastModifiedById());
        if(itemDto.getCreatedAt() != null) newItem.setCreatedAt(itemDto.getCreatedAt());
        if(itemDto.getActive() != null) newItem.setActive(itemDto.getActive());
        return newItem;
    }

    private void mapUpdateDtoToItem(Item item, ItemUpdateDto itemUpdateDto) {
        if(itemUpdateDto.getId() != null) item.setId(itemUpdateDto.getId());
        if(itemUpdateDto.getName() != null) item.setName(itemUpdateDto.getName());
        if(itemUpdateDto.getDescription() != null) item.setName(itemUpdateDto.getDescription());
        if(itemUpdateDto.getCategory() != null) item.setCategory(itemUpdateDto.getCategory());
        if(itemUpdateDto.getCategoryId() != null) item.setCategoryId(itemUpdateDto.getCategoryId());
        if(itemUpdateDto.getItemType() != null) item.setItemType(itemUpdateDto.getItemType());
        if(itemUpdateDto.getDateTzSensitive() != null) item.setDateTzSensitive(itemUpdateDto.getDateTzSensitive());
        if(itemUpdateDto.getDateTzSensitiveEnd() != null) item.setDateTzSensitiveEnd(itemUpdateDto.getDateTzSensitiveEnd());
        if(itemUpdateDto.getTimeSensitiveFlag() != null) item.setTimeSensitiveFlag(itemUpdateDto.getTimeSensitiveFlag());
        if(itemUpdateDto.getDateRangeFlag() != null) item.setDateRangeFlag(itemUpdateDto.getDateRangeFlag());
        if(itemUpdateDto.getDateTzInsensitive() != null) item.setDateTzInsensitive(itemUpdateDto.getDateTzInsensitive());
        if(itemUpdateDto.getDateTzInsensitive_end() != null) item.setDateTzInsensitiveEnd(itemUpdateDto.getDateTzInsensitive_end());
        if(itemUpdateDto.getPermissionLevel() != null) item.setPermissionLevel(itemUpdateDto.getPermissionLevel());
        if(itemUpdateDto.getCreatedById() != null) item.setCreatedById(itemUpdateDto.getCreatedById());
        if(itemUpdateDto.getLastModifiedById() != null) item.setLastModifiedById(itemUpdateDto.getLastModifiedById());
        if(itemUpdateDto.getCreatedAt() != null) item.setCreatedAt(itemUpdateDto.getCreatedAt());
        if(itemUpdateDto.getActive() != null) item.setActive(itemUpdateDto.getActive());
    }

    private ItemDto mapItemToItemDto(Item item) {
        ItemDto newItemDto = new ItemDto();
        if(item.getId() != null) newItemDto.setId(item.getId());
        if(item.getName() != null) newItemDto.setName(item.getName());
        if(item.getDescription() != null) newItemDto.setName(item.getDescription());
        if(item.getCategory() != null) newItemDto.setCategory(item.getCategory());
        if(item.getCategoryId() != null) newItemDto.setCategoryId(item.getCategoryId());
        if(item.getItemType() != null) newItemDto.setItemType(item.getItemType());
        if(item.getDateTzSensitive() != null) newItemDto.setDateTzSensitive(item.getDateTzSensitive());
        if(item.getDateTzSensitiveEnd() != null) newItemDto.setDateTzSensitiveEnd(item.getDateTzSensitiveEnd());
        if(item.getTimeSensitiveFlag() != null) newItemDto.setTimeSensitiveFlag(item.getTimeSensitiveFlag());
        if(item.getDateRangeFlag() != null) newItemDto.setDateRangeFlag(item.getDateRangeFlag());
        if(item.getDateTzInsensitive() != null) newItemDto.setDateTzInsensitive(item.getDateTzInsensitive());
        if(item.getDateTzInsensitiveEnd()!= null) newItemDto.setDateTzInsensitiveEnd(item.getDateTzInsensitiveEnd());
        if(item.getPermissionLevel() != null) newItemDto.setPermissionLevel(item.getPermissionLevel());
        if(item.getCreatedById() != null) newItemDto.setCreatedById(item.getCreatedById());
        if(item.getLastModifiedById() != null) newItemDto.setLastModifiedById(item.getLastModifiedById());
        if(item.getCreatedAt() != null) newItemDto.setCreatedAt(item.getCreatedAt());
        if(item.getActive() != null) newItemDto.setActive(item.getActive());
        return newItemDto;
    }
}
