package com.kollab.service;

import com.kollab.dto.list.ListPermissionDto;
import com.kollab.entity.list.KollabList;
import com.kollab.entity.list.ListPermission;
import com.kollab.repository.ListPermissionRepository;
import com.kollab.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class ListPermissionService {
    private final ListPermissionRepository listPermissionRepository;

    public ListPermissionService(ListPermissionRepository listPermissionRepository){
        this.listPermissionRepository = listPermissionRepository;
    }

    public ListPermissionDto joinList(ListPermissionDto listPermissionDto){
        ListPermission listPermission = mapListPermissionDtoToListPermission(listPermissionDto);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(listPermission.getUserId() == null) listPermission.setUserId(customUserDetails.getId());
        return mapListPermissionDtoToListPermission(listPermissionRepository.save(listPermission));
    }

    private ListPermission mapListPermissionDtoToListPermission(ListPermissionDto listPermissionDto){
        ListPermission listPermission = new ListPermission();
        if(listPermissionDto.getId() != null) listPermission.setId(listPermissionDto.getId());
        if(listPermissionDto.getListId() != null) listPermission.setListId(listPermissionDto.getListId());
        if(listPermissionDto.getUserId() != null) listPermission.setUserId(listPermissionDto.getUserId());
        if(listPermissionDto.getPermission() != null) listPermission.setPermission(listPermissionDto.getPermission());
        if(listPermissionDto.getCreatedAt() != null) listPermission.setCreatedAt(listPermissionDto.getCreatedAt());
        return listPermission;
    }

    private ListPermissionDto mapListPermissionDtoToListPermission(ListPermission listPermission){
        ListPermissionDto listPermissionDtoToReturn = new ListPermissionDto();
        if(listPermission.getId() != null) listPermissionDtoToReturn.setId(listPermission.getId());
        if(listPermission.getListId() != null) listPermissionDtoToReturn.setListId(listPermission.getListId());
        if(listPermission.getUserId() != null) listPermissionDtoToReturn.setUserId(listPermission.getUserId());
        if(listPermission.getPermission() != null) listPermissionDtoToReturn.setPermission(listPermission.getPermission());
        if(listPermission.getCreatedAt() != null) listPermissionDtoToReturn.setCreatedAt(listPermission.getCreatedAt());
        return listPermissionDtoToReturn;
    }

}
