package com.kollab.repository;

import com.kollab.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCreatedByIdAndCategoryIdIsNull(Long id);
}
