package com.kollab.repository;

import com.kollab.entity.list.ListPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListPermissionRepository extends JpaRepository<ListPermission, Long> {
    List<ListPermission> findByListIdAndUserId(Long listId, Long userId);
    Optional<List<ListPermission>> findByListId(Long listId);
}
