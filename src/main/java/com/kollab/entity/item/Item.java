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
    @Column(name = "category_id")
    private Long categoryId;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GENERAL'")
    @Column(name = "item_type")
    private ItemType itemType;
    @Nullable
    @Column(name = "date_tz_sensitive")
    private Date dateTzSensitive;
    @Nullable
    @Column(name = "date_tz_sensitive_end")
    private Date dateTzSensitiveEnd;
    @ColumnDefault("'false'")
    @Column(name = "time_sensitive_flag")
    private Boolean timeSensitiveFlag;
    @ColumnDefault("'false'")
    @Column(name = "date_range_flag")
    private Boolean dateRangeFlag;
    @Nullable
    @Column(name = "date_tz_insensitive")
    private String dateTzInsensitive;
    @Nullable
    @Column(name = "date_tz_insensitive_end")
    private String dateTzInsensitiveEnd;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PUBLIC'")
    @Column(name = "permission_level")
    private VisibilityLevel permissionLevel;
    @Column(name = "created_by_id")
    private Long createdById;
    @Column(name = "last_modified_by_id")
    private Long lastModifiedById;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="item_id", referencedColumnName = "id")
    @Column(name = "item_permissions")
    private List<ItemPermission> itemPermissions;
    @ColumnDefault("'true'")
    private Boolean active;
}
