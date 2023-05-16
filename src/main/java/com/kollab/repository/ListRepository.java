package com.kollab.repository;

import com.kollab.entity.list.KollabList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<KollabList, Long> {
}
