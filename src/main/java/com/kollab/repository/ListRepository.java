package com.kollab.repository;

import com.kollab.entity.list.KollabList;
import com.kollab.entity.list.ListPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListRepository extends JpaRepository<KollabList, Long> {
    List<KollabList> findByOwnerId(Long userid);
    List<KollabList> findByOwnerIdOrListPermissionsUserId(Long ownerId, Long userId);
}
