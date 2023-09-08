package com.kollab;

import com.kollab.dto.list.ListDto;
import com.kollab.dto.list.ListEditPasscodeDto;
import com.kollab.dto.list.ListUpdateDto;
import com.kollab.entity.User;
import com.kollab.entity.item.AccessLevel;
import com.kollab.entity.list.KollabList;
import com.kollab.entity.list.ListPermission;
import com.kollab.repository.ListPermissionRepository;
import com.kollab.repository.ListRepository;
import com.kollab.repository.UserRepository;
import com.kollab.service.ListService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ListServiceUnitTest {
    @Mock
    private ListRepository listRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ListPermissionRepository listPermissionRepository;
    @InjectMocks
    private ListService listService;
    @Test
    public void ListService_GetListUsers_ReturnListOfUserDto(){
        KollabList list = KollabList.builder()
                .id(1L)
                .name("unit test list")
                .passcode(String.valueOf(1445))
                .ownerId(1L)
                .createdAt(Date.from(Instant.now()))
                .build();
        User user1 = User.builder()
                .id(1L)
                .name("unit,test")
                .email("unittest@gmail.com")
                .password("unittest")
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("unit,test2")
                .email("unittest2@gmail.com")
                .password("unittest2")
                .build();
        ListPermission listPermission = ListPermission.builder()
                .userId(user1.getId())
                .list(list)
                .permission(AccessLevel.PUBLIC)
                .createdAt(Date.from(Instant.now()))
                .build();
        ListPermission listPermission2 = ListPermission.builder()
                .userId(user2.getId())
                .list(list)
                .permission(AccessLevel.PUBLIC)
                .createdAt(Date.from(Instant.now()))
                .build();
        List<ListPermission> listPermissionList = new ArrayList<>(List.of(listPermission, listPermission2));
        list.setListPermissions(listPermissionList);
        when(listRepository.findById(Mockito.any())).thenReturn(Optional.of(list));
        when(userRepository.findById(listPermission.getUserId())).thenReturn(Optional.of(user1));
        when(userRepository.findById(listPermission2.getUserId())).thenReturn(Optional.of(user2));
        try {
            Assertions.assertThat(listService.getListUsers(list.getId())).isNotNull();
        } catch (Exception e) {
            Assert.fail();
        }
    }
    @Test
    public void ListService_CreateList_ReturnListDtoOfCreatedList(){
        ListDto listDto = ListDto.builder()
                .id(1L)
                .description("unit test description")
                .name("unit test list")
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .ownerId(1L)
                .build();
        KollabList list = KollabList.builder()
                .id(1L)
                .description("unit test description")
                .name("unit test list")
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .ownerId(1L)
                .build();
        when(listRepository.save(Mockito.any())).thenReturn(list);
        Assertions.assertThat(listService.createList(listDto)).isNotNull();
    }
    @Test
    public void ListService_UpdateList_ReturnUpdatedListDto(){
        ListUpdateDto listUpdateDto = ListUpdateDto.builder()
                .id(1L)
                .description("unit test description updated")
                .build();
        KollabList list1 = KollabList.builder()
                .id(1L)
                .description("unit test description")
                .name("unit test list")
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .ownerId(1L)
                .build();
        KollabList list2 = KollabList.builder()
                .id(1L)
                .description("unit test description updated")
                .name("unit test list")
                .passcode(String.valueOf(1445))
                .createdAt(Date.from(Instant.now()))
                .ownerId(1L)
                .build();
        when(listRepository.save(Mockito.any())).thenReturn(list2);
        when(listRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(list1));
        try{
            Assertions.assertThat(listService.updateList(listUpdateDto)).isNotNull();
            Assertions.assertThat(listService.updateList(listUpdateDto).getDescription()).isEqualTo("unit test description updated");
        } catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void ListService_EditListPasscode_ReturnUpdatedListDto(){
        ListEditPasscodeDto listEditPasscodeDto = ListEditPasscodeDto.builder()
                .listId(1L)
                .oldPasscode(String.valueOf(1445))
                .passcode(String.valueOf(1446))
                .build();
        KollabList kollabList = KollabList.builder()
                .id(1L)
                .name("unit test list")
                .description("unit test description")
                .passcode(String.valueOf(1445))
                .ownerId(1L)
                .createdAt(Date.from(Instant.now()))
                .build();
        KollabList kollabList2 = KollabList.builder()
                .id(1L)
                .name("unit test list")
                .description("unit test description")
                .passcode(String.valueOf(1446))
                .ownerId(1L)
                .createdAt(Date.from(Instant.now()))
                .build();
        when(listRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(kollabList));
        when(listRepository.save(Mockito.any(KollabList.class))).thenReturn(kollabList2);
        try{
            ListDto listDto = listService.editPasscode(listEditPasscodeDto);
            Assertions.assertThat(listDto).isNotNull();
        } catch (Exception e){
            Assert.fail();
        }
    }
}
