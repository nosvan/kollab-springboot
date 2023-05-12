package com.kollab.dto;

import com.kollab.entity.Category;
import com.kollab.entity.item.ItemType;
import com.kollab.entity.item.VisibilityLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
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
public class ItemDto implements Serializable {
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Category category;
    private Long category_id;
    @NotNull
    private ItemType item_type;
    private Date date_tz_sensitive;
    private Date date_tz_sensitive_end;
    @NotNull
    private Boolean time_sensitive_flag;
    @NotNull
    private Boolean date_range_flag;
    private String date_tz_insensitive;
    private String date_tz_insensitive_end;
    @NotNull
    private VisibilityLevel permission_level;
    private Long created_by_id;
    private Long last_modified_by_id;
    private Date created_at;
    private Boolean active;
}
