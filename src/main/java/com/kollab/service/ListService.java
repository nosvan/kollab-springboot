package com.kollab.service;

import com.kollab.dto.list.ListDeleteDto;
import com.kollab.dto.list.ListDto;
import com.kollab.dto.list.ListEditPasscodeDto;
import com.kollab.dto.list.ListUpdateDto;
import com.kollab.dto.user.UserDto;
import com.kollab.entity.User;
import com.kollab.entity.item.AccessLevel;
import com.kollab.entity.list.KollabList;
import com.kollab.entity.list.ListPermission;
import com.kollab.repository.ListPermissionRepository;
import com.kollab.repository.ListRepository;
import com.kollab.repository.UserRepository;
import com.kollab.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.kollab.service.UserServiceImpl.mapUserToUserDto;

@Configuration
public class ListService {
    private final ListRepository listRepository;
    private final ListPermissionRepository listPermissionRepository;
    private final UserRepository userRepository;

    public ListService(ListRepository listRepository, ListPermissionRepository listPermissionRepository, UserRepository userRepository){
        this.listRepository = listRepository;
        this.listPermissionRepository = listPermissionRepository;
        this.userRepository = userRepository;
    }


    public List<ListDto> getOwnLists(){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<KollabList> lists = listRepository.findByOwnerId(customUserDetails.getId());
        return mapListToListsDto(lists);
    }

    public List<ListDto> getLists(){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<KollabList> lists = listRepository.findByOwnerIdOrListPermissionsUserId(customUserDetails.getId(), customUserDetails.getId());
        return mapListToListsDto(lists);
    }

    public List<UserDto> getListUsers(Long listId) throws Exception {
        Optional<KollabList> list = listRepository.findById(listId);
        if(list.isPresent()){
            List<ListPermission> listPermissions = list.get().getListPermissions();
            List<UserDto> userDtoListToReturn = new ArrayList<>();
            for(ListPermission lp : listPermissions){
                Optional<User> user = userRepository.findById(lp.getUserId());
                user.ifPresent(value -> userDtoListToReturn.add(mapUserToUserDto(value)));
            }
            return userDtoListToReturn;
        } else {
            throw new Exception("Error getting list users");
        }
    }

    public ListDto createList(ListDto listDto){
        KollabList list = mapListDtoToList(listDto);
        if(listDto.getOwnerId() == null){
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            list.setOwnerId(customUserDetails.getId());
        }
        list = listRepository.save(list);
        ListPermission listPermission = new ListPermission();
        listPermission.setList(list);
        listPermission.setUserId(list.getOwnerId());
        listPermission.setPermission(AccessLevel.ADMIN);
        listPermissionRepository.save(listPermission);
        return mapListToListDto(list);
    }

    public ListDto updateList(ListUpdateDto listUpdateDto) throws Exception {
        Optional<KollabList> listToUpdate = listRepository.findById(listUpdateDto.getId());
        if(listToUpdate.isPresent()){
            KollabList list = listToUpdate.get();
            mapListUpdateDtoToList(list, listUpdateDto);
            return mapListToListDto(listRepository.save(list));
        } else {
            throw new Exception("Error updating list");
        }
    }

    public ListDto editPasscode(ListEditPasscodeDto listEditPasscodeDto) throws Exception {
        Optional<KollabList> listToUpdate = listRepository.findById(listEditPasscodeDto.getListId());
        if(listToUpdate.isPresent() && listToUpdate.get().getPasscode().equals(listEditPasscodeDto.getOldPasscode())){
            KollabList list = listToUpdate.get();
            list.setPasscode(listEditPasscodeDto.getPasscode());
            return mapListToListDto(listRepository.save(list));
        } else {
            throw new Exception("Error updating list");
        }
    }

    public Long deleteList(ListDeleteDto listDeleteDto) throws Exception {
        Optional<KollabList> listToDelete = listRepository.findById(listDeleteDto.getId());
        if(listToDelete.isEmpty()) throw new Exception("Error deleting list");
        List<ListPermission> permissions = listToDelete.get().getListPermissions();
        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<ListPermission> userPermission = permissions.stream().filter(p -> Objects.equals(p.getUserId(), userId) && p.getPermission().equals(AccessLevel.ADMIN)).findFirst();
        if(userPermission.isEmpty()) throw new Exception("User does not have correct permission");
        listRepository.deleteById(listToDelete.get().getId());
        return listToDelete.get().getId();
    }

    private void mapListUpdateDtoToList(KollabList list, ListUpdateDto listUpdateDto){
        if(listUpdateDto.getId() != null) list.setId(listUpdateDto.getId());
        if(listUpdateDto.getDescription() != null) list.setDescription(listUpdateDto.getDescription());
        if(listUpdateDto.getOwnerId() != null) list.setOwnerId(listUpdateDto.getOwnerId());
        if(listUpdateDto.getPasscode() != null) list.setPasscode(listUpdateDto.getPasscode());
        if(listUpdateDto.getCreatedAt() != null) list.setCreatedAt(listUpdateDto.getCreatedAt());
        if(listUpdateDto.getId() != null) list.setId(listUpdateDto.getId());
    }

    private KollabList mapListDtoToList(ListDto listDto){
        KollabList list = new KollabList();
        list.setId(listDto.getId());
        list.setName(listDto.getName());
        list.setDescription(listDto.getDescription());
        list.setOwnerId(listDto.getOwnerId());
        list.setPasscode(listDto.getPasscode());
        list.setCreatedAt(listDto.getCreatedAt());
        return list;
    }

    private ListDto mapListToListDto(KollabList list){
        ListDto listDto = new ListDto();
        listDto.setId(list.getId());
        listDto.setName(list.getName());
        listDto.setDescription(list.getDescription());
        listDto.setCreatedAt(list.getCreatedAt());
        return listDto;
    }

    private List<ListDto> mapListToListsDto(List<KollabList> list){
        List<ListDto> listsDto = new ArrayList<>();
        for(KollabList l : list){
            listsDto.add(mapListToListDto(l));
        }
        return listsDto;
    }
}
