package com.kollab.entity.list;

import com.kollab.entity.User;
import com.kollab.entity.item.AccessLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="list_permission",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"list_id", "user_id"})})
public class ListPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name="list_id")
    private KollabList list;
    @Column(name="user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private AccessLevel permission = AccessLevel.PUBLIC;
    @CreationTimestamp
    @Column(name="created_at")
    private Date createdAt;
}
