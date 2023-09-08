package com.kollab.service;

import com.kollab.dto.list.ListJoinDto;
import com.kollab.dto.list.ListPermissionDto;
import com.kollab.dto.user.UsersWithPermissionForListDto;
import com.kollab.entity.User;
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
public class ListPermissionService {
    private final ListPermissionRepository listPermissionRepository;
    private final ListRepository listRepository;

    public ListPermissionService(ListPermissionRepository listPermissionRepository, ListRepository listRepository){
        this.listPermissionRepository = listPermissionRepository;
        this.listRepository = listRepository;
    }

    public ListPermissionDto joinList(ListJoinDto listJoinDto) throws Exception {
        Optional<KollabList> list = listRepository.findById(listJoinDto.getListId());
        if(list.isEmpty()) throw new Exception("Error finding list with that id");
        if(!list.get().getPasscode().equals(listJoinDto.getPasscode())) throw new Exception("Invalid credentials");
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ListPermission listPermission = new ListPermission();
        listPermission.setList(list.get());
        listPermission.setUserId(customUserDetails.getId());
        return mapListPermissionToListPermissionDto(listPermissionRepository.save(listPermission));
    }

    private List<UsersWithPermissionForListDto> mapUsersToUsersWithPermissionForListDto(List<User> users){
        List<UsersWithPermissionForListDto> usersWithPermissionForListDtoList = new ArrayList<>();
        for(User u : users){
            UsersWithPermissionForListDto usersWithPermissionForListDto = new UsersWithPermissionForListDto();
            usersWithPermissionForListDto.setUserId(u.getId());
            String[] name = u.getName().split(",");
            usersWithPermissionForListDto.setFirstName(name[0]);
            usersWithPermissionForListDto.setLastName(name[1]);
            usersWithPermissionForListDto.setEmail(u.getEmail());
            usersWithPermissionForListDtoList.add(usersWithPermissionForListDto);
        }
        return usersWithPermissionForListDtoList;
    }

    private ListPermission mapListPermissionDtoToListPermission(ListPermissionDto listPermissionDto){
        ListPermission listPermission = new ListPermission();
        if(listPermissionDto.getId() != null) listPermission.setId(listPermissionDto.getId());
        if(listPermissionDto.getListId() != null) {
            Optional<KollabList> list = listRepository.findById(listPermissionDto.getListId());
            list.ifPresent(listPermission::setList);
        }
        if(listPermissionDto.getUserId() != null) listPermission.setUserId(listPermissionDto.getUserId());
        if(listPermissionDto.getPermission() != null) listPermission.setPermission(listPermissionDto.getPermission());
        if(listPermissionDto.getCreatedAt() != null) listPermission.setCreatedAt(listPermissionDto.getCreatedAt());
        return listPermission;
    }

    private ListPermissionDto mapListPermissionToListPermissionDto(ListPermission listPermission){
        ListPermissionDto listPermissionDtoToReturn = new ListPermissionDto();
        if(listPermission.getId() != null) listPermissionDtoToReturn.setId(listPermission.getId());
        if(listPermission.getList() != null) listPermissionDtoToReturn.setListId(listPermission.getList().getId());
        if(listPermission.getUserId() != null) listPermissionDtoToReturn.setUserId(listPermission.getUserId());
        if(listPermission.getPermission() != null) listPermissionDtoToReturn.setPermission(listPermission.getPermission());
        if(listPermission.getCreatedAt() != null) listPermissionDtoToReturn.setCreatedAt(listPermission.getCreatedAt());
        return listPermissionDtoToReturn;
    }

}
