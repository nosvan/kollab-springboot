package com.kollab.dto.item;

import com.kollab.entity.list.Category;
import com.kollab.entity.item.ItemType;
import com.kollab.entity.item.VisibilityLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDto implements Serializable {
    @NotNull
    private Long id;
    private String name;
    private String description;
    private Category category;
    private Long categoryId;
    private ItemType itemType;
    private Date dateTzSensitive;
    private Date dateTzSensitiveEnd;
    private Boolean timeSensitiveFlag;
    private Boolean dateRangeFlag;
    private String dateTzInsensitive;
    private String dateTzInsensitive_end;
    private VisibilityLevel permissionLevel;
    private Long createdById;
    private Long lastModifiedById;
    private Date createdAt;
    private Boolean active;
}
