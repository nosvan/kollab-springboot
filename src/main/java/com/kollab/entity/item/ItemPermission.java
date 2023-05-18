package com.kollab.entity.item;

import com.kollab.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="item_permission",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"item_id", "user_id"}))
public class ItemPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "user_id")
    private Long userId;
    @CreationTimestamp
    @Column(name="created_at")
    private Date createdAt;
}
