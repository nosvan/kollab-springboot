package com.kollab.entity.list;

import com.kollab.entity.item.Item;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="list_id", referencedColumnName = "id")
    private List<ListPermission> listPermissions;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private List<Item> items;
}
