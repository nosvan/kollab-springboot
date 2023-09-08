package com.kollab.entity.item;

import com.kollab.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name="item_permission",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"item_id", "user_id"}))
public class ItemPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column(name = "user_id")
    private Long userId;
    @CreationTimestamp
    @Column(name="created_at")
    private Date createdAt;
}
