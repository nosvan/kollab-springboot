package com.kollab.repository;

import com.kollab.entity.item.ItemPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPermissionRepository extends JpaRepository<ItemPermission, Long> {
    List<ItemPermission> findByItemId(Long itemId);
}
