package com.kollab.repository;

import com.kollab.entity.item.Item;
import com.kollab.entity.list.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCreatedByIdAndCategoryIdIsNull(Long id);
    List<Item> findByCategoryAndCategoryId(Category category, Long categoryId);
}
