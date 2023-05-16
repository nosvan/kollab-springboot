package com.kollab.service;

import com.kollab.dto.list.ListDeleteDto;
import com.kollab.dto.list.ListDto;
import com.kollab.dto.list.ListUpdateDto;
import com.kollab.entity.list.KollabList;
import com.kollab.repository.ListRepository;
import com.kollab.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class ListService {
    private final ListRepository listRepository;

    public ListService(ListRepository listRepository){
        this.listRepository = listRepository;
    }

    public void saveList(ListDto listDto){
        KollabList list = mapListDtoToList(listDto);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        list.setOwnerId(customUserDetails.getId());
        listRepository.save(list);
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
}
