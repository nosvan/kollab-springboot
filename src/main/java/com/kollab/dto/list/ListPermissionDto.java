package com.kollab.dto.list;

import com.kollab.entity.item.AccessLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListPermissionDto {
    private Long id;
    @NotNull
    private Long listId;
    private Long userId;
    private AccessLevel permission;
    private Date createdAt;
}
