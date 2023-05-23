package com.kollab;

import com.kollab.dto.item.ItemDto;
import com.kollab.entity.item.Item;
import com.kollab.entity.item.ItemType;
import com.kollab.entity.item.VisibilityLevel;
import com.kollab.repository.ItemRepository;
import com.kollab.security.CustomUserDetails;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
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
}
