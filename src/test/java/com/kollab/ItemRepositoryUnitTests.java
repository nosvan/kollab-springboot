package com.kollab;

import com.kollab.entity.item.Item;
import com.kollab.entity.item.ItemPermission;
import com.kollab.entity.item.ItemType;
import com.kollab.entity.item.VisibilityLevel;
import com.kollab.repository.ItemPermissionRepository;
import com.kollab.repository.ItemRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class ItemRepositoryUnitTests {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemPermissionRepository itemPermissionRepository;

    @Test
    @Order(1)
    public void ItemRepository_SaveOwnItem_ReturnsSavedOwnItem(){
        Item item = Item.builder()
                .id(1L)
                .name("unit test")
                .itemType(ItemType.GENERAL)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .createdAt(Date.from(Instant.now()))
                .createdById(600L).build();
        Item savedItem = itemRepository.save(item);
        Assertions.assertThat(savedItem).isNotNull();
        Assertions.assertThat(savedItem.getCreatedById()).isNotNull();
        Assertions.assertThat(savedItem.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedItem.getCreatedById()).isEqualTo(600L);
    }

    @Test
    @Order(2)
    public void ItemRepository_SaveItemAndItemPermission_ReturnItemAndPermission() {
        //Build item and its two permissions
        Item item = Item.builder()
                .name("unit test")
                .itemType(ItemType.GENERAL)
                .permissionLevel(VisibilityLevel.PRIVATE)
                .createdAt(Date.from(Instant.now()))
                .createdById(600L).build();
        ItemPermission itemPermission = ItemPermission.builder()
                .userId(item.getCreatedById())
                .createdAt(Date.from(Instant.now()))
                .item(item)
                .build();
        ItemPermission itemPermission2 = ItemPermission.builder()
                .userId(item.getCreatedById())
                .createdAt(Date.from(Instant.now()))
                .item(item)
                .build();
        item.setItemPermissions(List.of(itemPermission, itemPermission2));
        Item itemReturned = itemRepository.save(item);
        //Return item from repository
        Optional<Item> item2 = itemRepository.findById(itemReturned.getId());
        if (item2.isEmpty()) fail();
        Item item3 = item2.get();
        Assertions.assertThat(item3).isNotNull();
        Assertions.assertThat(item3.getItemPermissions()).isNotNull();
        Assertions.assertThat(item3.getItemPermissions().get(0).getItem().getId()).isEqualTo(item3.getId());
        Assertions.assertThat(item3.getItemPermissions().get(0).getUserId()).isEqualTo(item3.getCreatedById());
    }

    @Test
    @Order(3)
    public void ItemRepository_SaveAndDeleteItem_ReturnsNull(){
        Item item = Item.builder()
                .name("unit test")
                .itemType(ItemType.GENERAL)
                .permissionLevel(VisibilityLevel.PRIVATE)
                .createdAt(Date.from(Instant.now()))
                .createdById(600L).build();
        Item item2 = itemRepository.save(item);
        itemRepository.delete(item2);
        Optional<Item> item3 = itemRepository.findById(item2.getId());
        Assertions.assertThat(item3.isEmpty()).isEqualTo(true);
    }

    @Test
    @Order(4)
    public void ItemRepository_SaveItemWithPermissionsAndThenDeleteItem_CheckIfPermissionsAreAlsoDeleted(){
        //Build item and its two permissions
        Item item = Item.builder()
                .name("unit test")
                .itemType(ItemType.GENERAL)
                .permissionLevel(VisibilityLevel.PRIVATE)
                .createdAt(Date.from(Instant.now()))
                .createdById(600L).build();
        ItemPermission itemPermission = ItemPermission.builder()
                .userId(item.getCreatedById())
                .createdAt(Date.from(Instant.now()))
                .item(item)
                .build();
        ItemPermission itemPermission2 = ItemPermission.builder()
                .userId(item.getCreatedById())
                .createdAt(Date.from(Instant.now()))
                .item(item)
                .build();
        item.setItemPermissions(List.of(itemPermission, itemPermission2));
        Item item2 = itemRepository.save(item);
        itemRepository.deleteById(item2.getId());
        Optional<ItemPermission> itemPermissionList = itemPermissionRepository.findById(item2.getItemPermissions().get(0).getItem().getId());
        Assertions.assertThat(itemPermissionList).isEmpty();
    }
}
