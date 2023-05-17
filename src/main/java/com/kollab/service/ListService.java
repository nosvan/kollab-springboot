package com.kollab.service;

import com.kollab.dto.list.ListDeleteDto;
import com.kollab.dto.list.ListDto;
import com.kollab.dto.list.ListUpdateDto;
import com.kollab.entity.item.AccessLevel;
import com.kollab.entity.list.KollabList;
import com.kollab.entity.list.ListPermission;
import com.kollab.repository.ListPermissionRepository;
import com.kollab.repository.ListRepository;
import com.kollab.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class ListService {
    private final ListRepository listRepository;
    private final ListPermissionRepository listPermissionRepository;

    public ListService(ListRepository listRepository, ListPermissionRepository listPermissionRepository){
        this.listRepository = listRepository;
        this.listPermissionRepository = listPermissionRepository;
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

    public ListDto createList(ListDto listDto){
        KollabList list = mapListDtoToList(listDto);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        list.setOwnerId(customUserDetails.getId());
        list = listRepository.save(list);
        ListPermission listPermission = new ListPermission();
        listPermission.setListId(list.getId());
        listPermission.setUserId(list.getOwnerId());
        listPermission.setPermission(AccessLevel.ADMIN);
        listPermissionRepository.save(listPermission);
        return mapListToListDto(list);
    }

    public void updateList(ListUpdateDto listUpdateDto) throws Exception {
        Optional<KollabList> listToUpdate = listRepository.findById(listUpdateDto.getId());
        if(listToUpdate.isPresent()){
            KollabList list = listToUpdate.get();
            mapListUpdateDtoToList(list, listUpdateDto);
            listRepository.save(list);
        } else {
            throw new Exception("Error updating list");
        }
    }

    public void deleteList(ListDeleteDto listDeleteDto) throws Exception {
        Optional<KollabList> listToDelete = listRepository.findById(listDeleteDto.getId());
        if(listToDelete.isPresent()){
            listRepository.deleteById(listToDelete.get().getId());
        } else {
            throw new Exception("Error deleting list");
        }
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
        listDto.setOwnerId(list.getOwnerId());
        listDto.setPasscode(list.getPasscode());
        listDto.setCreatedAt(list.getCreatedAt());
        return listDto;
    }

    private List<ListDto> mapListToListsDto(List<KollabList> list){
        List<ListDto> listsDto = new ArrayList<ListDto>();
        for(KollabList l : list){
            listsDto.add(mapListToListDto(l));
        }
        return listsDto;
    }
}
