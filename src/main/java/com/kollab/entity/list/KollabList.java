package com.kollab.entity.list;

import com.kollab.entity.item.Item;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="list")
public class KollabList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Nullable
    private String description;
    @Column(name="owner_id")
    private Long ownerId;
    private String passcode;
    @CreationTimestamp
    @Column(name="created_at")
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "list")
    private List<ListPermission> listPermissions;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "list")
    private List<Item> items;
}
