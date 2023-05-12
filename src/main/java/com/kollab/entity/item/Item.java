package com.kollab.entity.item;

import com.kollab.entity.Category;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Nullable
    private String description;
    @Nullable
    @Enumerated(EnumType.STRING)
    private Category category;
    @Nullable
    private Long category_id;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GENERAL'")
    private ItemType item_type;
    @Nullable
    private Date date_tz_sensitive;
    @Nullable
    private Date date_tz_sensitive_end;
    @ColumnDefault("'false'")
    private Boolean time_sensitive_flag;
    @ColumnDefault("'false'")
    private Boolean date_range_flag;
    @Nullable
    private String date_tz_insensitive;
    @Nullable
    private String date_tz_insensitive_end;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PUBLIC'")
    private VisibilityLevel permission_level;
    private Long created_by_id;
    private Long last_modified_by_id;
    @CreationTimestamp
    private Date created_at;
    @OneToMany
    @JoinColumn(name="item_id", referencedColumnName = "id")
    private List<ItemPermission> item_permissions;
    @ColumnDefault("'true'")
    private Boolean active;
}
