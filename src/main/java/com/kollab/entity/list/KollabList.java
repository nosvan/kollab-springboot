package com.kollab.entity.list;

import com.kollab.entity.item.Item;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer owner_id;
    private String passcode;
    private Date created_at;
    @OneToMany
    @JoinColumn(name="list_id", referencedColumnName = "id")
    private List<ListPermission> list_permissions;
    @OneToMany
    @JoinColumn(name="list_id", referencedColumnName = "id")
    private List<Item> items;

}
