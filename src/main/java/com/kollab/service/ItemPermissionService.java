package com.kollab.service;

import com.kollab.dto.item.ItemPermissionDto;
import com.kollab.entity.item.ItemPermission;
import com.kollab.repository.ItemPermissionRepository;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemPermissionService {
    private final ItemPermissionRepository itemPermissionRepository;

    public ItemPermissionService(ItemPermissionRepository itemPermissionRepository) {
        this.itemPermissionRepository = itemPermissionRepository;
    }

    public static List<ItemPermissionDto> mapItemPermissionToItemPermissionDto(List<ItemPermission> itemPermissionList){
        List<ItemPermissionDto> itemPermissionDtoList = new ArrayList<>();
        for(ItemPermission ip : itemPermissionList){
            ItemPermissionDto itemPermissionDto = new ItemPermissionDto();
            itemPermissionDto.setId(ip.getId());
            itemPermissionDto.setUserId(ip.getUserId());
            itemPermissionDto.setItemId(ip.getItem().getId());
            itemPermissionDto.setCreatedAt(ip.getCreatedAt());
        }
        return itemPermissionDtoList;
    }
}
