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
    private Long list_id;
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "list_id")
    private KollabList list;
    private Long user_id;
    @OneToOne
    @JoinColumn(name="id", referencedColumnName = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PUBLIC'")
    private AccessLevel permission;
    @CreationTimestamp
    private Date created_at;
}
