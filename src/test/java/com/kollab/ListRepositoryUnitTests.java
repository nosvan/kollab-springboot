package com.kollab;

import com.kollab.entity.item.*;
import com.kollab.entity.list.KollabList;
import com.kollab.entity.list.ListPermission;
import com.kollab.repository.ItemPermissionRepository;
import com.kollab.repository.ItemRepository;
import com.kollab.repository.ListPermissionRepository;
import com.kollab.repository.ListRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class ListRepositoryUnitTests {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private ListPermissionRepository listPermissionRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemPermissionRepository itemPermissionRepository;

    @Test
    public void ListRepository_SaveList_ReturnList(){
        KollabList list = KollabList.builder()
                .name("test list")
                .ownerId(600L)
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .build();
        KollabList list2 = listRepository.save(list);
        Assertions.assertThat(list2).isNotNull();
    }

    @Test
    public void ListRepository_SaveListAndPermission_ReturnListWithPermission(){
        KollabList list = KollabList.builder()
                .name("test list")
                .ownerId(600L)
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .build();
        ListPermission listPermission = ListPermission.builder()
                .userId(list.getOwnerId())
                .createdAt(Date.from(Instant.now()))
                .list(list)
                .permission(AccessLevel.PUBLIC)
                .build();
        list.setListPermissions(List.of(listPermission));
        KollabList list2 = listRepository.save(list);
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2.getListPermissions()).isNotNull();
        Assertions.assertThat(list2.getListPermissions().get(0).getUserId()).isEqualTo(600L);
        Optional<List<ListPermission>> listPermissions = listPermissionRepository.findByListId(list2.getId());
        if(listPermissions.isEmpty()){
            fail();
        } else {
            Assertions.assertThat(listPermissions.get().get(0).getList().getId()).isEqualTo(list2.getId());
        }
    }

    @Test
    public void ListRepository_SaveListAndPermissionThenDeleteList_ListDeletedAndRelatedPermissions(){
        KollabList list = KollabList.builder()
                .name("test list")
                .ownerId(600L)
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .build();
        ListPermission listPermission = ListPermission.builder()
                .userId(list.getOwnerId())
                .createdAt(Date.from(Instant.now()))
                .list(list)
                .permission(AccessLevel.PUBLIC)
                .build();
        list.setListPermissions(List.of(listPermission));
        KollabList list2 = listRepository.save(list);
        listRepository.deleteById(list2.getId());
        Optional<KollabList> list3 = listRepository.findById(list2.getId());
        list3.ifPresent(l->fail());
        Optional<List<ListPermission>> listPermissions = listPermissionRepository.findByListId(list2.getId());
        listPermissions.ifPresentOrElse(permissions-> Assertions.assertThat(permissions.size()).isEqualTo(0), Assert::fail);
    }

    public void ListRepository_SaveListAndPermissionAndItemsThenDeleteList_ListDeletedAlongWithListPermissionsAndItems(){
        KollabList list = KollabList.builder()
                .name("test list")
                .ownerId(600L)
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .build();
        ListPermission listPermission = ListPermission.builder()
                .userId(list.getOwnerId())
                .createdAt(Date.from(Instant.now()))
                .list(list)
                .permission(AccessLevel.PUBLIC)
                .build();
        Item item = Item.builder()
                .name("unit test")
                .itemType(ItemType.GENERAL)
                .timeSensitiveFlag(false)
                .dateRangeFlag(false)
                .permissionLevel(VisibilityLevel.PUBLIC)
                .createdById(list.getOwnerId())
                .createdAt(Date.from(Instant.now()))
                .active(true)
                .list(list)
                .build();
        ItemPermission itemPermission = ItemPermission.builder()
                .userId(item.getCreatedById())
                .createdAt(Date.from(Instant.now()))
                .item(item)
                .build();
        item.setItemPermissions(List.of(itemPermission));
        list.setListPermissions(List.of(listPermission));
        list.setItems(List.of(item));
        KollabList list2 = listRepository.save(list);
        listRepository.deleteById(list2.getId());
        Optional<KollabList> list3 = listRepository.findById(list2.getId());
        list3.ifPresent(l->fail());
        List<Item> itemsList = itemRepository.findByCreatedByIdAndCategoryIdIsNull(list2.getId());
        Assertions.assertThat(itemsList.size()).isEqualTo(0);
        List<ItemPermission> itemPermissionList = itemPermissionRepository.findByItemId(list2.getItems().get(0).getId());
        Assertions.assertThat(itemPermissionList.size()).isEqualTo(0);
    }
}
