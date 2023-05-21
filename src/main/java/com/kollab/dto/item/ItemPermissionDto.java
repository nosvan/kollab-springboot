package com.kollab.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPermissionDto {
    private Long id;
    private Long itemId;
    private Long userId;
    private Date createdAt;
}
