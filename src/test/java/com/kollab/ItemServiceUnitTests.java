package com.kollab;

import com.kollab.dto.item.ItemDto;
import com.kollab.dto.item.ItemUpdateDto;
import com.kollab.entity.item.Item;
import com.kollab.entity.item.ItemPermission;
import com.kollab.entity.item.ItemType;
import com.kollab.entity.item.VisibilityLevel;
import com.kollab.entity.list.Category;
import com.kollab.repository.ItemRepository;
import com.kollab.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ItemServiceUnitTests {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemService itemService;

    @Test
    public void ItemService_getItem_ReturnItem(){
        Item item = Item.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(item));
        try {
            assert item != null;
            Item itemReturned = itemService.getItem(item.getId().toString());
            Assertions.assertThat(itemReturned).isNotNull();
            Assertions.assertThat(itemReturned.getName()).isEqualTo("unit test");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void ItemService_CreateItem_ReturnItemDto(){
        Item item = Item.builder()
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        ItemDto itemDto = ItemDto.builder()
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);
        when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(item));
        try {
            ItemDto savedItemDto = itemService.createItem(itemDto);
            Assertions.assertThat(savedItemDto).isNotNull();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void ItemService_UpdateItem_NoException(){
        Item item = Item.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        Item item2 = Item.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.MEETING)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        ItemUpdateDto itemUpdateDto = ItemUpdateDto.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.MEETING)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(item));
        when(itemRepository.save(Mockito.any())).thenReturn(item2);
        try{
            Assertions.assertThat(itemService.updateItem(itemUpdateDto).getItemType()).isEqualTo(ItemType.MEETING);
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void ItemService_DeleteItem_NoException(){
        Item itemToDelete = Item.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(itemToDelete));
        try{
            assert itemToDelete != null;
            itemService.deleteItem(itemToDelete.getId().toString());
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void ItemService_GetItems_ReturnFetchedItems(){
        Item item = Item.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .category(Category.LIST)
                .categoryId(2L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        when(itemRepository.findByCategoryAndCategoryId(Mockito.any(), Mockito.any())).thenReturn(List.of(item));
        Assertions.assertThat(itemService.getItems(Category.LIST, item.getCategoryId().toString()).get(0).getName()).isEqualTo("unit test");
    }

    @Test
    public void ItemService_GetItemPermissions_ReturnPermissionsDto(){
        Item item = Item.builder()
                .id(1L)
                .name("unit test")
                .createdById(600L)
                .itemType(ItemType.GENERAL)
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .dateRangeFlag(false)
                .timeSensitiveFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .build();
        ItemPermission itemPermission = ItemPermission.builder()
                .id(2L)
                .item(item)
                .userId(600L)
                .createdAt(Date.from(Instant.now()))
                .build();
        ItemPermission itemPermission2 = ItemPermission.builder()
                .id(3L)
                .item(item)
                .userId(600L)
                .createdAt(Date.from(Instant.now()))
                .build();
        item.setItemPermissions(List.of(itemPermission, itemPermission2));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        try{
            Assertions.assertThat(itemService.getItemPermissions(item.getId().toString())).isNotNull();
        } catch (Exception e){
            Assert.fail();
        }
    }
}
